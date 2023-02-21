package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.util.*;

public class InMemoryCourseRepository implements CourseRepository {
/*
    pensalo come una arrayList(NON fanno parte della stassa famiglia) ma le posizioni vengono definite con degli id UNIVOCI
    immaginalo come 2 colonne a sinistra l'id UNIVOCO della riga e a destra un oggetto
 */
    private static Map<Long,Course> dataSource = new HashMap<>();
    private static long nextId;

    /*
        Optional lo vedo un pò come una variabile jolly in che senso:
        Viene utitlizzata soprattutto (o forse unicamente[questo ce lo dirà il tempo]) per gestire variabili/isatnze/puntaori che posso essere null, come?
        Optional non può essere null, optional sarà EMPTY se gli passiamo un valore null e PRESENT se gli passiamo qualcosa che non sia null
        Come vedi si dichiarano con le generics --> <>
        all'interno ci mettiamo il tipo di dato che devono ricevere (lo sai insomma)
        MA COSA FA?
        dice al programmatore che bisogna fare un controllo (nomeOptional.isEmpty) se non lo fa è un coglione!
        si! hai capito!! serve "solo" per ricordarci/ o a dire di controllare se un dato è vuoto(null) o meno, così da evitare cappellate logiche durante la scrittura dei codici
     */

    @Override
    public Optional<Course> findById(long id) {
        Course x = dataSource.get(id);
        if (x != null){
            return Optional.of(x);
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findByTitleContains(String part){
        List<Course> result = new ArrayList<>();            //un pò di polimorfismo non fa mai male
        Collection<Course> cs = dataSource.values();        //rappresenta una collezione di oggetti non ordinati(messi alla cazzo di cane) si ci possiamo ciclare sopra, guarda il for
        for (Course c:cs){
            if (c.getTitle().contains(part)){
                result.add(c);                              //aggiungiamo l'oggetto che abbiamo trovato nella collection alla lista
            }
        }
        return result;
    }

    @Override
    public Course create(Course course) {
        nextId++;
        dataSource.put(nextId, course);
        course.setId(nextId);
        return course;
    }

    @Override
    public void update(Course course) throws EntityNotFoundException {
        if (dataSource.containsKey(course.getId())){
            dataSource.put(course.getId(), course);                   //inseriamo l'oggeto nel hashMap
        }else {
//            EntityNotFoundException e = new EntityNotFoundException("Non esiste un corso con id: " + course.getId());
//            throw e;
            throw new EntityNotFoundException("Non esiste un corso con id: " + course.getId());
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException {
//        Course old = dataSource.remove(id);
//        if (old == null){
//            throw new EntityNotFoundException("Non esiste un corso con id: " + id);
//        }
        if (dataSource.remove(id)==null){           //possiamo farlo perche .remove() ritornerà null se non trova l' id
            throw new EntityNotFoundException("Non esiste un corso con id: " + id);
        }
    }

    public ArrayList<Course> getActiveCourses() {
        ArrayList<Course> actives = new ArrayList<>();
        Collection<Course> cs = dataSource.values();        //rappresenta una collezione di oggetti non ordinati(messi alla cazzo di cane) si ci possiamo ciclare sopra, guarda il for
        for (Course c:cs){
            if (c.isActive()){
                actives.add(c);                              //aggiungiamo l'oggetto che abbiamo trovato nella collection alla lista
            }
        }
        return actives;
    }

    @Override
    public void deleteOldestActiveCourses(int num) {
        ArrayList<Course> orderedActives = getActiveCourses();
        /* Collections.sort(orderedActives);
        Collections.sort(orderedActives, new CourseComparatorByTitleLength());
        Collections.sort(orderedActives, new Comparator<Course>() { // classe interna anonima
            @Override
            public int compare(Course o1, Course o2) {
                return (o1.getTitle().length() - o2.getTitle().length());
            }
        });
        Collections.sort(orderedActives, (o1, o2) -> (o1.getTitle().length() - o2.getTitle().length()));*/ // lambda expression, function literal

        orderedActives.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())); //lo sorta ascendente o discendente?
        int count = num;
        while (count > 0) {
            orderedActives.get(orderedActives.size()-1).setActive(false);
            orderedActives.remove(orderedActives.size()-1);
            --count;
        }
    }


}

//class CourseComparatorByTitleLength implements Comparator<Course> {
//
//    @Override
//    public int compare(Course o1, Course o2) {
//        /* if (o1.getTitle().length() > o2.getTitle().length()) {
//            return 1;
//        } else if (o1.getTitle().length() < o2.getTitle().length()) {
//            return -1;
//        } else {
//            return 0;
//        } */
//        return (o1.getTitle().length() - o2.getTitle().length());
//    }
//}

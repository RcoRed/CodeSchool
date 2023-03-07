package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.entities.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.time.LocalDate;
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

    public static Map<Long, Course> getDataSource() {
        return dataSource;
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(dataSource.values());
    }

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
    /*public int countActiveCourses(List<Course> courses){
        int count=0;
        for(Course c : courses){
            if(c.isActive()){
                ++count;
            }
        }
        return count;
    }*/
    public List<Course> createListOfActiveCourses2(List<Course> courses){
        List<Course> activeCourses = new ArrayList<>();
        for(Course c : courses){
            if (c.isActive()){
                activeCourses.add(c);
            }
        }
        return activeCourses;
    }

    public List<Course> cancelOldActiveCourses2(List<Course> courses, int difference){
        List<Course> activeCourses = createListOfActiveCourses2(courses);
        Course course = new Course();
        for (int z = 0; z<difference; z++) {
            for (int i = 0; i < activeCourses.size(); i++) {
                Course course1 = activeCourses.get(i);
                if(course.getCreateAt().isBefore(course1.getCreateAt())) {
                    course1 = course;
                }
                for (int j = 0; j < activeCourses.size() - i; j++) {
                    Course course2 = activeCourses.get(j + i);
                    if (course1.getCreateAt().isAfter(course2.getCreateAt())) {
                        course = course2;
                    }
                }
            }
            activeCourses.remove(course);       //prova ad usare il for con Iterator
        }
        return activeCourses;
    }

    public ArrayList<Course> createListOfActiveCourses(){
        ArrayList<Course> activeCourses = new ArrayList<>();
        Collection<Course> cs = dataSource.values();
        for(Course c : cs){
            if (c.isActive()){
                activeCourses.add(c);
            }
        }
        return activeCourses;
    }
    public void cancelOldActiveCourses(int difference) {
        ArrayList<Course> activeCourses = createListOfActiveCourses();
        //Collections.sort(activeCourses);
        //Collections.sort(activeCourses, new CourseComparatorByTitleLength());  //posso chiamare un oggetto che possieda dentro di se la funzione che voglio usare
        //Collections.sort(activeCourses, (o1, o2) -> (o1.getTitle().length() - o2.getTitle().length()));
        //Collections.sort(activeCourses, (o1,o2) -> o1.getCreateAt().compareTo(o2.getCreateAt()));

        activeCourses.sort((o1,o2) -> o2.getCreateAt().compareTo(o1.getCreateAt())); //più vecchie alla fine
        int count = difference;
        while(count>0){
            activeCourses.get(activeCourses.size()-1).setActive(false);
            activeCourses.remove(activeCourses.size()-1);
            --count;
        }
    }
}

/*class CourseComparatorByTitleLength implements Comparator<Course>{

    @Override
    public int compare(Course o1, Course o2) {
        /*if(o1.getTitle().length() > o2.getTitle().length()){
            return 1; // se il primo è più grande del secondo ritorna
                       //positivo se voglio ordinare dal più grande al più piccolo
        } else if (o1.getTitle().length() < o2.getTitle().length()) {
            return -1;
        } else {
            return 0;
        }//
        return (o1.getTitle().length() - o2.getTitle().length());
        // basta ritornare la differenza delle lunghezze
    }
}*/

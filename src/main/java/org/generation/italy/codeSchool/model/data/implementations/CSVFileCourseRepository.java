package org.generation.italy.codeSchool.model.data.implementations;

import org.generation.italy.codeSchool.model.Course;
import org.generation.italy.codeSchool.model.data.abstractions.CourseRepository;
import org.generation.italy.codeSchool.model.data.exceptions.DataException;
import org.generation.italy.codeSchool.model.data.exceptions.EntityNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.generation.italy.codeSchool.model.data.Constants.*;

public class CSVFileCourseRepository implements CourseRepository{
    private String fileName;
    private static long nextId;
    public static final String DEFAULT_FILE_NAME = "Corsi.csv";

    public CSVFileCourseRepository() {
        this.fileName = DEFAULT_FILE_NAME;
    }
    public CSVFileCourseRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Optional<Course> findById(long id) throws DataException{             //!!RICORDATI!! se un metodo può dare un errore allora DEVI mettere il THROWS e l'exception che "lancerà"
        try{
            List<String> lines = Files.readAllLines(Paths.get(fileName));       //apro il file
            for (String s:lines){                                               //ciclo per ogni riga letta
                String[] trimmed = s.split(",");                          //uso un metodo della classe String che creerà una nuova stringa per ogni , che incontrerà, ogni stringa verrà salvata in un array
                long courseId = Long.parseLong(trimmed[0]);
                if (courseId == id){
                    Course found = new Course(courseId,trimmed[1],trimmed[2]    //creo l'oggeto passando le stringhe letta dal file
                            ,trimmed[3],Double.parseDouble(trimmed[4]));
                    return Optional.of(found);
                }
            }
            return Optional.empty();
        }catch (IOException e){                                                  //"raccogliamo" IOException ma "lanciamo" un DataException di nostra creazione (si, si può fare) così da poter ancora utilizzare la nostra interfaccia nel miglior modo possibile
            throw new DataException("Errore nella lettura del file",e);          //lo lanciamo qui
        }
    }

    @Override
    public List<Course> findByTitleContains(String part) throws DataException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            List<Course> courses = new ArrayList<>();
            for(String s : lines){
                String[] tokens = s.split(",");
                if(tokens[1].contains(part)){
                    Course found = new Course(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                            tokens[3], Double.parseDouble(tokens[4]));
                    courses.add(found);
                }
            }
            return courses;
        }catch (IOException e){
            throw new DataException("Errore nella lettura del file", e);
        }
    }

    @Override
    public Course create(Course course) throws DataException{
        /*
            FileOutputStream serve per scrivere nel file                !!(magari da richiedere)!!
            quel (append)true serve ad aggiungere una nuova riga alle riche esistenti, se non ci fosse sovrascriverebbe tutte le righe presenti nel file
            PintWriter sarà colui che effettivamente scriverà sul file
         */
        try (FileOutputStream output = new FileOutputStream(fileName,true);
                PrintWriter pw = new PrintWriter(output)){
            course.setId(++nextId);
            pw.println(CourseToCSV(course));                //è qui che scrivo sul file (si con una println) richiamando un metodo creato da noi(sta verso la fine)
            return course;                                  //ovviamente nelle parentesi gli passo la stringa che voglio sivere sul file
        }catch (IOException e){
            throw new DataException("Errore nel salvataggio su file",e);
        }
    }

    @Override
    public void update(Course course) throws EntityNotFoundException, DataException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String s : lines) {
                String[] tokens = s.split(",");
                if (course.getId()==Long.parseLong(tokens[0])){
                    deleteById(Long.parseLong(tokens[0]));
                    create(course);
                    /*try (FileOutputStream output = new FileOutputStream(fileName,true);
                         PrintWriter pw = new PrintWriter(output)){
                        pw.println(CourseToCSV(course));
                    }*/
                }
            }
        }catch (IOException e){
            throw new DataException("Errore nel salvataggio su file", e);
        }
    }

    @Override
    public void deleteById(long id) throws EntityNotFoundException, DataException {
        try{
            List<String> lines= Files.readAllLines(Paths.get(fileName));
            for(Iterator<String> it = lines.iterator(); it.hasNext();){
                String line = it.next();
                String[] tokens = line.split(",");
                long courseId=Long.parseLong(tokens[0]);
                if (courseId==id){
                    it.remove();
                    try(PrintWriter pw = new PrintWriter(new FileOutputStream(fileName))) {
                        for (String st : lines) {
                            pw.println(st);
                        }
                    }
                    return;
                }
            }
            throw new EntityNotFoundException(ENTITY_NOT_FOUND + id);
        }catch(IOException e){
            throw new DataException("Errore nel cancellamento di una linea da file CSV", e);
        }

    }

    public String CourseToCSV(Course c){                //trasforma i dati presenti dell'oggetto in una stringa(che poi scriveremo sul file)
        return String.format(Locale.US,"%d,%s,%s,%s,%.2f",c.getId(),c.getTitle()
                ,c.getDescription(),c.getProgram(),c.getDuration());
    }

}

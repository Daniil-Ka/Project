package org.basicprogramming.loaders;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.basicprogramming.db.DB;
import org.basicprogramming.db.models.Exercise;
import org.basicprogramming.db.models.Lesson;
import org.basicprogramming.db.models.Mark;
import org.basicprogramming.db.models.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XLSLLoader implements TableLoader {
    private static final int basicProgrammingSheetIndex = 0;

    private final String path;

    private Sheet basicProgrammingSheet;

    private HashMap<Lesson, List<Exercise>> lessons;
    private List<Exercise> exercises;

    public XLSLLoader(String path) {
        this.path = path;
    }

    public void load() throws IOException {
        FileInputStream file = new FileInputStream(this.path);
        Workbook workbook = new XSSFWorkbook(file);

        basicProgrammingSheet = workbook.getSheetAt(basicProgrammingSheetIndex);

        readAll();
    }

    public void readAll() {
        var lessons = getAllLessons();
        var students = new ArrayList<Student>();

        for (var lesson : lessons.keySet()) {
            DB.addRecord(lesson);
        }

        for (var exercises : lessons.values()) {
            for (var exercise : exercises) {
                DB.addRecord(exercise);
            }
        }

        for (var index = 3; index < basicProgrammingSheet.getLastRowNum(); index++) {
            var row = basicProgrammingSheet.getRow(index);
            var student = readStudent(row);
            DB.addRecord(student);
        }
    }

    public Student readStudent(Row row) {
        var student = new Student();
        parseStudentData(row, student);
        parseStudentMarks(row, student);
        return student;
    }

    private Student parseStudentData(Row row, Student student) {
        String name = row.getCell(0).getStringCellValue();
        var splitName = name.split(" ", 2);
        if (splitName.length == 2) {
            student.setFirstName(splitName[0]);
            student.setLastName(splitName[1]);
        }
        else {
            student.setFirstName(name);
        }

        student.setUlearnId(UUID.fromString(row.getCell(1).getStringCellValue()));
        student.setMail(row.getCell(2).getStringCellValue());

        return student;
    }

    private Student parseStudentMarks(Row row, Student student) {
        var exercises = getAllExercises();
        var marks = new ArrayList<Mark>();

        for (var i = 0; i < exercises.size(); i++) {
            var score = (int)row.getCell(i + 4).getNumericCellValue();
            var mark = new Mark(student, exercises.get(i), score);
            marks.add(mark);
        }

        student.setMarks(marks);
        return student;
    }

    public HashMap<Lesson, List<Exercise>> getAllLessons() {
        if (lessons != null)
            return lessons;

        var lessons = new LinkedHashMap<Lesson, List<Exercise>>();
        var exericiseRow = basicProgrammingSheet.getRow(1);

        for (var region : getLessonRegions()) {
            var lessonName = basicProgrammingSheet
                    .getRow(region.getFirstRow())
                    .getCell(region.getFirstColumn())
                    .getStringCellValue();
            var lesson = new Lesson(lessonName);

            var exercises = new ArrayList<Exercise>();

            for (var i = region.getFirstColumn(); i <= region.getLastColumn(); i++) {
                var cell = exericiseRow.getCell(i);
                var text = cell.getStringCellValue();
                var newExercise = new Exercise(text, lesson);
                exercises.add(newExercise);
            }
            lessons.put(lesson, exercises);
        }
        this.lessons = lessons;
        return lessons;
    }

    public List<Exercise> getAllExercises() {
        if (exercises != null)
            return exercises;

        var exercises = new ArrayList<Exercise>();

        for (var exercisesForLesson: getAllLessons().values()) {
            exercises.addAll(exercisesForLesson);
        }

        this.exercises = exercises;
        return exercises;
    }

    public List<CellRangeAddress> getLessonRegions() {
        var result = new ArrayList<CellRangeAddress>();
        var headerRow = basicProgrammingSheet.getRow(0);
        var regions = basicProgrammingSheet.getMergedRegions();

        for (var region : regions) {
            int columnIndex = region.getFirstColumn();
            int rowIndex = region.getFirstRow();

            if (rowIndex == 0 && columnIndex != 0) {
                result.add(region);
            }
        }

        return result;
    }
}
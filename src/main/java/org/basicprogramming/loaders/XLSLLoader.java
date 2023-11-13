package org.basicprogramming.loaders;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.basicprogramming.db.models.Exercise;
import org.basicprogramming.db.models.Lesson;
import org.basicprogramming.db.models.Student;
import org.jfree.chart.LegendItemSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class XLSLLoader implements TableLoader {
    private static final int basicProgrammingSheetIndex = 0;

    private final String path;

    private Sheet basicProgrammingSheet;

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


        var students = new ArrayList<Student>();

        for (var index = 3; index < basicProgrammingSheet.getLastRowNum(); index++) {
            var row = basicProgrammingSheet.getRow(index);
            var student = new Student();

        }
    }

    public Student readStudent(Row row) {
        var student = new Student();
        parseStudentData(row, student);
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

        var groupText = row.getCell(3).getStringCellValue();
        return student;
    }

    public List<Lesson> getLessons() {
        var lessons = new ArrayList<Lesson>();
        for (var region : getLessonRegions()) {
            var text = basicProgrammingSheet
                    .getRow(region.getFirstColumn())
                    .getCell(region.getFirstRow())
                    .getStringCellValue();
            var lesson = new Lesson(text);
            lessons.add(lesson);
        }
        return lessons;
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

    public List<Exercise> getExerciseRanges(CellRangeAddress lessonRange, Lesson lesson) {
        var exercises = new ArrayList<Exercise>();
        var start = lessonRange.getFirstColumn();
        var end = lessonRange.getFirstRow();
        var exericiseRow = basicProgrammingSheet.getRow(1);

        for (var i = start; i < end; i++) {
            var cell = exericiseRow.getCell(i);
            var text = cell.getStringCellValue();

            var newExercise = new Exercise(text, lesson);
            exercises.add(newExercise);
        }

        return exercises;
    }
}
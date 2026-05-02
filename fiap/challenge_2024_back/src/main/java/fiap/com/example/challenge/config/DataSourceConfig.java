package fiap.com.example.challenge.config;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Component
public class DataSourceConfig {
    private final DataSource dataSource;

    @Autowired
    public DataSourceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runDataScriptsAfterStartup() {
        try {
            readExcelAndInsertData();
            System.out.println("Excel data imported successfully.");
        } catch (IOException | SQLException e) {
            System.err.println("Failed to import data from Excel: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void readExcelAndInsertData() throws IOException, SQLException {
        String excelFilePath = new ClassPathResource("data/data.xlsx").getFile().getAbsolutePath();

        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
             Connection connection = dataSource.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0);

            String sql = "INSERT INTO Task (number, opened, updated, report_state, report_resolved, report_category, report_subcategory, symptom," +
                    "symptom_qualification, assignment_group, category, assigned_to, priority, closed, requester, location," +
                    "description, visible_comments1, visible_comments2, visible_comments3, visible_comments4, visible_comments5," +
                    "visible_comments6, visible_comments7, visible_comments8, visible_comments9, resolution) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue; // Skip header row
                    }

                    preparedStatement.setString(1, row.getCell(0).getStringCellValue()); // number
                    preparedStatement.setTimestamp(2, new java.sql.Timestamp(row.getCell(1).getDateCellValue().getTime())); // opened
                    preparedStatement.setTimestamp(3, new java.sql.Timestamp(row.getCell(2).getDateCellValue().getTime())); // updated
                    preparedStatement.setString(4, row.getCell(3).getStringCellValue()); // report_state
                    preparedStatement.setTimestamp(5, new java.sql.Timestamp(row.getCell(4).getDateCellValue().getTime())); // report_resolved
                    preparedStatement.setString(6, row.getCell(5).getStringCellValue()); // report_category
                    preparedStatement.setString(7, row.getCell(6).getStringCellValue()); // report_subcategory
                    preparedStatement.setString(8, row.getCell(7).getStringCellValue()); // symptom
                    preparedStatement.setString(9, row.getCell(8).getStringCellValue()); // symptom_qualification
                    preparedStatement.setString(10, row.getCell(9).getStringCellValue()); // assignment_group
                    preparedStatement.setString(11, row.getCell(10).getStringCellValue()); // category
                    preparedStatement.setString(12, row.getCell(11).getStringCellValue()); // assigned_to
                    preparedStatement.setString(13, row.getCell(12).getStringCellValue()); // priority
                    preparedStatement.setTimestamp(14, new java.sql.Timestamp(row.getCell(13).getDateCellValue().getTime())); // closed
                    preparedStatement.setString(15, row.getCell(14).getStringCellValue()); // requester
                    preparedStatement.setString(16, row.getCell(15).getStringCellValue()); // location
                    preparedStatement.setString(17, row.getCell(16).getStringCellValue()); // description
                    preparedStatement.setString(18, row.getCell(17).getStringCellValue()); // visible_comments1
                    preparedStatement.setString(19, row.getCell(18).getStringCellValue()); // visible_comments2
                    preparedStatement.setString(20, row.getCell(19).getStringCellValue()); // visible_comments3
                    preparedStatement.setString(21, row.getCell(20).getStringCellValue()); // visible_comments4
                    preparedStatement.setString(22, row.getCell(21).getStringCellValue()); // visible_comments5
                    preparedStatement.setString(23, row.getCell(22).getStringCellValue()); // visible_comments6
                    preparedStatement.setString(24, row.getCell(23).getStringCellValue()); // visible_comments7
                    preparedStatement.setString(25, row.getCell(24).getStringCellValue()); // visible_comments8
                    preparedStatement.setString(26, row.getCell(25).getStringCellValue()); // visible_comments9
                    preparedStatement.setString(27, row.getCell(26).getStringCellValue()); // resolution

                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}

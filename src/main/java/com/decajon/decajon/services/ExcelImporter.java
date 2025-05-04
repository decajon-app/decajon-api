package com.decajon.decajon.services;

import com.decajon.decajon.dto.*;
import com.decajon.decajon.mappers.GroupMapper;
import com.decajon.decajon.mappers.UserMapper;
import com.decajon.decajon.models.Group;
import com.decajon.decajon.models.User;
import com.decajon.decajon.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "decajon.excelimport.enabled", havingValue = "true")
public class ExcelImporter implements CommandLineRunner
{
    // Services
    private final UserService userService;
    private final GroupService groupService;
    private final RepertoireService repertoireService;

    // Group ID for insert the repertoire
    private Long groupId;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Excel file path
        String excelFile = "src/main/resources/xlsx/Repertorio Mariachi Mexcalli.xlsx";

        if(new File(excelFile).exists())
        {
            System.out.println("Searching file [" + excelFile + "]");
            System.out.println("File found, creating Mariachi Mexcalli group...");
            createMariachiMexcalli();

            System.out.println("Reading data...");
            importRepertoire(excelFile);
        }
        else
        {
            System.out.println("Archivo no encontrado.");
        }
    }

    private void importRepertoire(String filePath) throws Exception
    {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        if(rowIterator.hasNext()) rowIterator.next(); // this skips the headers row

        while(rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            String songTitle = getStringValue(row.getCell(0));
            String artist = getStringValue(row.getCell(1));
            String tone = getStringValue(row.getCell(4));
            tone = (tone == null || tone.isEmpty()) ? "" : tone.substring(0,2);

            RepertoireRequestDto repertoireDto = new RepertoireRequestDto(
                    groupId,
                    songTitle,
                    artist,
                    tone
            );

            repertoireService.addSong(repertoireDto);
        }

        System.out.println("Repertoire has been imported from excel file successfully...");
    }

    private void createMariachiMexcalli()
    {
        // Creating Edgar Dto
        UserRequestDto edgarDto = new UserRequestDto(
                "edgar@mexcalli.com",
                "decajon1234",
                "Edgar",
                "Gonzalez"
        );
        // Creating Edgar user
        UserDto edgar = userService.createUser(edgarDto);

        // Creating Mexcalli Dto
        CreateGroupDto mexcalliDto = new CreateGroupDto("Mariachi Mexcalli", edgar.getId());
        // Creating Mexcalli group
        GroupDto mexcalli = groupService.createGroup(mexcalliDto);

        this.groupId = mexcalli.getId();
    }

    private Long getIntegerValue(Cell cell)
    {
        if(cell == null) return null;
        if(cell.getCellType() == CellType.NUMERIC)
        {
            return (long) cell.getNumericCellValue();
        }
        else if (cell.getCellType() == CellType.STRING)
        {
            try
            {
                return (long )Integer.parseInt(cell.getStringCellValue());
            }
            catch (NumberFormatException e)
            {
                return null;
            }
        }
        return null;
    }

    private String getStringValue(Cell cell)
    {
        if(cell == null) return null;
        return cell.getStringCellValue() != null ? cell.getStringCellValue().trim() : null;
    }
}



























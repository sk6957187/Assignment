package com.project.main.org.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFile {
	private OracleSqlConfig oracleSqlConfig;
	private CsvConfig csvConfig;

	public CsvFile(OracleSqlConfig oracleSqlConfig, CsvConfig csvConfig) {
		this.oracleSqlConfig = oracleSqlConfig;
		this.csvConfig = csvConfig;
	}

	public List<String> readCSVFile(){
		String filePath = System.getProperty("user.dir") + File.separator + csvConfig.getFileName();
		try{
			File file = new File(filePath);
			boolean fileExists = file.exists();
			if(!fileExists){
				System.err.println("File not exist");
				return null;
			}
			List<String> existingLines = new ArrayList<>();
			existingLines = Files.readAllLines(file.toPath());
			return existingLines;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String loadInCSVfile() {
		SQL sql = new SQL(oracleSqlConfig);
		ArrayList<ArrayList<String>> data = sql.readSQL();
		
	    String[] headers = csvConfig.getHeaders().toArray(new String[0]);
	    String fileName = csvConfig.getFileName();
	    String filePath = System.getProperty("user.dir") + File.separator + fileName;

	    try {
	        File file = new File(filePath);
	        boolean fileExists = file.exists();

	        StringBuilder content = new StringBuilder();

	        // Add headers if file doesn't exist
	        if (!fileExists) {
	            content.append(String.join(",", headers)).append("\n");
	        }

	        // Read existing content for update check (optional in CSV unless maintaining versioning)
	        List<String> existingLines = new ArrayList<>();
	        if (fileExists) {
	            existingLines = Files.readAllLines(file.toPath());
	        }

	        for (ArrayList<String> rowData : data) {
	            String sno = rowData.get(0);
	            String updateTime = rowData.get(8);
	            boolean isUpdated = false;

	            for (int i = 1; i < existingLines.size(); i++) {
	                String[] parts = existingLines.get(i).split(",", -1);
	                if (parts[0].equals(sno)) {
	                    if (!parts[8].equals(updateTime)) {
	                        existingLines.set(i, String.join(",", rowData));
	                    }
	                    isUpdated = true;
	                    break;
	                }
	            }

	            if (!isUpdated) {
	                content.append(String.join(",", rowData)).append("\n");
	            }
	        }

	        // Write final content to file
	        FileWriter writer = new FileWriter(filePath);
	        if (!fileExists) {
	            writer.write(content.toString());
	        } else {
	            for (String line : existingLines) {
	                writer.write(line + "\n");
	            }
	            writer.write(content.toString()); // write any new rows
	        }
	        writer.close();

	        return "CSV file updated at: " + filePath;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error updating CSV file.";
	    }
	}


	public List<String> filterData(List<String> str) {
		ArrayList<String> searchData = csvConfig.getSearchData();
		int col = csvConfig.getColumn();
		List<String> filteredData = new ArrayList<>();
		int totalRec = 0;

		for (String line : str) {
			String[] column = line.split(",", -1);

			if (column.length >= col ) {
				String mobile = column[col].trim();
				if (searchData.contains(mobile)) {
					filteredData.add(line);
					totalRec++;
				}
			}
		}

		System.err.println("Total filtered Records: " + totalRec);
		filteredData.add("Total Records: " + totalRec);
		return filteredData;
	}



	public String writeFile(List<String> str) {
		String[] headers = csvConfig.getHeaders().toArray(new String[0]);
		String fileName = csvConfig.getWriteFileName();
		String filePath = System.getProperty("user.dir") + File.separator + fileName;

		try {
			File file = new File(filePath);
			boolean fileExists = file.exists();

			FileWriter writer = new FileWriter(file, true); // append mode

			if (!fileExists) {
				writer.write(String.join(",", headers) + "\n");
			}

			for (String line : str) {
				writer.write(line + "\n");
			}

			writer.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}

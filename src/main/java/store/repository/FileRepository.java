package store.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.exception.RepositoryInitException;

public abstract class FileRepository extends Repository {

    private static final String INVALID_FILE_FORM_MESSAGE_FORMAT = "파일 형식을 확인해주세요.: %s";
    private static final String IO_EXCEPTION_MESSAGE_FORMAT = "파일을 IO하는 동안 예외가 발생하였습니다.: %s";

    private final File resourceFile;

    protected FileRepository(String filePath) {
        resourceFile = new File(filePath);
    }

    protected List<Map<String, String>> getTableData() {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            extractToData(data);
        } catch (IOException e) {
            String message = String.format(IO_EXCEPTION_MESSAGE_FORMAT, resourceFile.getPath());
            throw new RepositoryInitException(message);
        }

        return data;
    }

    private void extractToData(List<Map<String, String>> data) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(resourceFile));
        List<String> headers = splitLine(br.readLine());

        String line;
        while ((line = br.readLine()) != null) {
            Map<String, String> lineData = new HashMap<>();
            injectToLineData(lineData, headers, splitLine(line));
            data.add(lineData);
        }
    }

    private List<String> splitLine(String line) {
        return List.of(line.split(","));
    }

    private void injectToLineData(Map<String, String> lineData, List<String> headers, List<String> splitLine) {
        if (headers.size() != splitLine.size()) {
            String message = String.format(INVALID_FILE_FORM_MESSAGE_FORMAT, resourceFile.getPath());
            throw new RepositoryInitException(message);
        }

        for (int lineIndex = 0; lineIndex < headers.size(); lineIndex++) {
            lineData.put(headers.get(lineIndex), splitLine.get(lineIndex));
        }
    }
}

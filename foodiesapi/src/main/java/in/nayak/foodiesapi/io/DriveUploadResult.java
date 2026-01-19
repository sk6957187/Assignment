package in.nayak.foodiesapi.io;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriveUploadResult {
    private String fileId;
    private String webViewLink;
}

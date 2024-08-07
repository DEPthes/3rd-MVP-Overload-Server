package mvp.deplog.infrastructure.s3.application;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import mvp.deplog.infrastructure.s3.S3FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class S3FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadFile(MultipartFile file, String dirName) {
        String originalFileName = file.getOriginalFilename();
        String saveFileName = S3FileUtil.createSaveFileName(originalFileName); // uuid . ext

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        String filePath = dirName + saveFileName;
        try (InputStream inputStream = file.getInputStream()) {
            // S3에 업로드
            amazonS3.putObject(new PutObjectRequest(bucket, filePath, inputStream, metadata));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }

        return S3FileUtil.getFullPath(bucket, filePath);
    }

    @Override
    public void deleteFile(String fileName, String dirName) {
        String filePath = dirName + fileName;
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));
        } catch (AmazonServiceException e) {
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }
}

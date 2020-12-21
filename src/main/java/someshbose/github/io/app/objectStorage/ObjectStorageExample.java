package someshbose.github.io.app.objectStorage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetObjectTagsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.SetObjectTagsArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.minio.messages.Tags;
import someshbose.github.io.app.dto.CatDto;

public class ObjectStorageExample {
  public static void main(String inputParam, CatDto dto)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      // Create a minioClient with the MinIO server playground, its access key and secret key.
      MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000")
          .credentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY").build();

      final String minioBucket = "cat-bucket";

      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucket).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucket).build());
      } else {
        System.out.println("Bucket " + minioBucket + " already exists.");
      }

      // TO DO-1
      minioClient.putObject(PutObjectArgs.builder().bucket(minioBucket).object(inputParam)
          .stream(new ByteArrayInputStream(dto.toString().getBytes(StandardCharsets.UTF_8)), dto.toString().length(),
              -1)
          .build());
      // TO DO-2
      Map<String, String> map = new HashMap<>();
      map.put("Project", "Project One");
      minioClient.setObjectTags(SetObjectTagsArgs.builder().bucket(minioBucket).object(inputParam).tags(map).build());
      
      System.out.println("Successfully uploaded the file.");
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
    }
  }

  public static GetObjectResponse getCatList(String inputParam)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      List<CatDto> list = new ArrayList<CatDto>();
      final String minioBucket = "cat-bucket";

      // Create a minioClient with the MinIO server playground, its access key and secret key.
      MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1:9000")
          .credentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY").build();

      // Make 'cat-bucket' bucket if not exist.
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucket).build());
      if (!found) {
        // Make a new bucket called 'cat-bucket'.
        System.out.println("Bucket " + minioBucket + " doesn't  exists.");
        return null;
      }

      Optional<GetObjectResponse> response =
          Optional.of(minioClient.getObject(GetObjectArgs.builder().bucket(minioBucket).object(inputParam).build()));

      if (response.isPresent()) {
        return response.get();
      }

      // To do -2
      
        Tags tags = minioClient.getObjectTags(
        GetObjectTagsArgs.builder().bucket(minioBucket).object(inputParam).build());
        
        System.out.println(tags.get().get("Project"));
       

      // To do- 3
      Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(minioBucket).build());

      for (Result<Item> result : myObjects) {
        Item item = result.get();
        System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());


        // Generate a presigned URL which expires in a day
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
            .bucket(minioBucket).object(item.objectName()).expiry(60 * 60 * 24).build());

        System.out.println(item.etag());
        System.out.println(url);

      }
      

    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
    }
    return null;
  }
}

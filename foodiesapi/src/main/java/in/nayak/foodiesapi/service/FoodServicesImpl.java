package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.entity.FoodEntity;
import in.nayak.foodiesapi.io.FoodRequest;
import in.nayak.foodiesapi.io.FoodResponse;
import in.nayak.foodiesapi.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServicesImpl implements FoodService {

    private final S3Client s3Client;
    private final FoodRepository foodRepository;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            if (response.sdkHttpResponse().isSuccessful()) {
                return "https://" + bucketName + ".s3.amazonaws.com/" + key;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed to S3.");
            }

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while uploading the file", e);
        }
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        FoodEntity foodEntity = convertToEntity(request);
        String imageUrl = uploadFile(file);
        foodEntity.setImageUrl(imageUrl);
        foodEntity = foodRepository.save(foodEntity);
        return convertToResponse(foodEntity);
    }

    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> databaseEntries = foodRepository.findAll();
        return databaseEntries.stream()
                .map(this::convertToResponse)
                .collect(java.util.stream.Collectors.toList()); // ✅ FIX
    }

    @Override
    public FoodResponse readFood(String id) {
         FoodEntity existingFood = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found" +
                 " for this id: "+id));
         return convertToResponse(existingFood);
    }

    @Override
    public boolean deleteFile(String filename) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        s3Client.deleteObject(request);
        return true;
    }

    @Override
    public void deleteFood(String id){
        FoodResponse response = readFood(id);
        String imageUrl = response.getImageUrl();
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        boolean isFileDelete = deleteFile(filename);
        if(isFileDelete){
            foodRepository.deleteById(response.getId());
        }
    }

    @Override
    public FoodResponse updateFood(FoodEntity request, MultipartFile file) {
        FoodEntity existingFood = foodRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found with ID: " + request.getId()));
        if (file != null && !file.isEmpty()) {
            String oldImageUrl = existingFood.getImageUrl();
            if (oldImageUrl != null && oldImageUrl.contains("/")) {
                String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
                deleteFile(oldFileName);  // Safely ignore failure if file doesn't exist
            }

            String newImageUrl = uploadFile(file);
            existingFood.setImageUrl(newImageUrl);
        }

        existingFood.setName(request.getName());
        existingFood.setDescription(request.getDescription());
        existingFood.setPrice(request.getPrice());
        existingFood.setCategory(request.getCategory());

        FoodEntity updatedFood = foodRepository.save(existingFood);
        return convertToResponse(updatedFood);
    }


    private FoodEntity convertToEntity(FoodRequest request) {
        return FoodEntity.builder()
                .id(UUID.randomUUID().toString()) // generating ID here
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory()) // assuming it's in request
                .build();
    }

    private FoodResponse convertToResponse(FoodEntity entity) {
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}

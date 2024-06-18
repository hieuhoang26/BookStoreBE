package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.Auth.Role;
import com.bookstore.common.model.Auth.UserHasRole;
import com.bookstore.common.model.Shop;
import com.bookstore.common.model.ShopDetail;
import com.bookstore.common.model.User;
import com.bookstore.common.repository.ShopRepository;
import com.bookstore.common.service.RoleService;
import com.bookstore.common.service.ShopDetailService;
import com.bookstore.common.service.ShopService;
import com.bookstore.common.service.UserService;
import com.bookstore.modules.shop.dto.ShopDetailsDto;
import com.bookstore.modules.shop.dto.request.ShopRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImp implements ShopService {
    private final ShopRepository shopRepository;
    final UserService userService;
    final ShopDetailService shopDetailService;
    final RoleService roleService;

    @Override
    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public Shop getShopByBookId(Integer bookId) {
        return shopRepository.findShopByBookId(bookId);
    }

    @Override
    public Shop getShopById(Integer id) {
        return shopRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public Shop getShopByUserId(Integer id) {
        return shopRepository.findShopByUserId(id);
    }

    @Transactional
    @Override
    public void createShop(ShopRequest request) {

        String imageDir = "D:/BookStore/TestImg/";
        String fileName = "";
        String imagePath = "";
        try {
            User user = userService.getUserById(request.getUserId());
            if (user.getShop() != null) {
                throw new IllegalStateException("User already has a shop.");
            }
            MultipartFile image = request.getShopLogo();
            if (image != null && !image.isEmpty()) {
                fileName = "image_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                imagePath = imageDir + fileName;
                image.transferTo(new File(imagePath));
            }
            Shop shop = Shop.builder()
                    .shopName(request.getShopName())
                    .shopLogo(fileName)
                    .shopAddress(request.getShopAddress())
                    .contactPhone(request.getContactPhone())
                    .contactEmail(request.getContactEmail())
                    .build();
            ShopDetail shopDetail = ShopDetail.builder()
                    .description(request.getDescription())
                    .operatingHours(request.getOperationHours())
                    .shippingPolicy(request.getShippingPolicy())
                    .returnPolicy(request.getReturnPolicy())
                    .build();
            shop.addShopDetail(shopDetail);
            save(shop);
            // add shop for user
            user.addShop(shop);

            // add role for user
            Role role = roleService.getByName("ROLE_Shop");
            user.addRole(role);
            userService.saveUser(user);
            // get role return for fe;
//            List<String> roles = roleService.retrieveRoleByUserName(user.getUsername())
//                    .stream().map(role1 -> new String(role1.getName())).collect(Collectors.toList());
        } catch (Exception e) {
            try {
                Files.deleteIfExists(new File(imagePath).toPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Transactional
    @Override
    public void updateShop(ShopRequest request) {
        String imageDir = "D:/BookStore/TestImg/";
        String fileName = "";
        String imagePath = "";
        try {
            Shop shop = getShopByUserId(request.getUserId());

            // Delete old image if it exists
            String oldImages = shop.getShopLogo();
            File oldFile = new File(imageDir + oldImages);
            if (oldFile.exists()) {
                boolean isDeleted = oldFile.delete();
                if (!isDeleted) {
                    throw new IOException("Failed to delete old image file: " + oldFile.getPath());
                }
            }

            // Save new image
            MultipartFile image = request.getShopLogo();
            if (image != null && !image.isEmpty()) {
                fileName = "image_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
                imagePath = imageDir + fileName;
                image.transferTo(new File(imagePath));
            }

            // Update shop details
            shop.setShopName(request.getShopName());
            shop.setShopLogo(fileName);
            shop.setShopAddress(request.getShopAddress());
            shop.setContactPhone(request.getContactPhone());
            shop.setContactEmail(request.getContactEmail());

            ShopDetail shopDetail = shop.getShopDetail();
            shopDetail.setDescription(request.getDescription());
            shopDetail.setOperatingHours(request.getOperationHours());
            shopDetail.setShippingPolicy(request.getShippingPolicy());
            shopDetail.setReturnPolicy(request.getReturnPolicy());

            save(shop);
        } catch (IOException e) {
            try {
                Files.deleteIfExists(new File(imagePath).toPath());
            } catch (IOException ioException) {
                System.err.println("Failed to delete the new image file: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void deleteShop(Integer userId) {
        User user = userService.getUserById(userId);
        Shop shop = getShopByUserId(userId);
        user.setShop(null);
        userService.saveUser(user);
        // delete user role
//        userService.deleteRoleForUser(userId, 2);
        Role role = roleService.getByName("ROLE_Shop");
        user.removeRole(role);
        shopRepository.delete(shop);
        // update role
//        List<String> roles = roleService.retrieveRolesByUserId(userId)
//                .stream().map(role -> new String(role.getName())).collect(Collectors.toList());
    }

    @Override
    public ShopDetailsDto getShopDetailForShop(Integer shopId) {
        ShopDetail shopDetail = shopRepository.findShopDetailsByShopId(shopId);
        return ShopDetailsDto.builder()
                .operationHours(shopDetail.getOperatingHours())
                .description(shopDetail.getDescription())
                .shippingPolicy(shopDetail.getShippingPolicy())
                .returnPolicy(shopDetail.getReturnPolicy())
                .build();
    }

    @Override
    public void updateShopDetail(Integer shopId, ShopDetailsDto request) {
        ShopDetail shopDetail = shopRepository.findShopDetailsByShopId(shopId);
        shopDetail.setDescription(request.getDescription());
        shopDetail.setOperatingHours(request.getOperationHours());
        shopDetail.setShippingPolicy(request.getShippingPolicy());
        shopDetail.setReturnPolicy(request.getReturnPolicy());
        shopDetailService.save(shopDetail);
    }
}

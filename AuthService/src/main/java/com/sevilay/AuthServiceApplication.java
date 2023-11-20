package com.sevilay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }

    /**
     *
     * 1-
     * -> 7071' de ayağa kalkan bir UserService modülü oluşturalım.
     * -> Postgre bağlantılarını gerçekleştirelim
     * ---> SocialMediaAuthDB
     * ---> SocialMediaUserDB
     *
     * 2-
     * -> Repository
     * -> Service
     * -> Controller katmanlarını oluşturalım
     * - register methodu yazalım ve buna bir endpoint verelim
     * bu işlemleri request ve response dto ile gerçekleştirelim
     *
     * 3-
     * -> Dışarıdan login olmak için gerekli bilgileri alalım.
     * - Eğer bilgiler doğru ise true, hatalıysa false dönelim.
     *
     * 4-
     * -> Register olduktan sonra bize bir activationCode u geliyor.
     * - Bu activationCode u kullanarak User ın Status unu değiştirelim.
     * - Buna uygun bir method yazalım.
     *
     * 5-
     * -> Login methodumuzu düzenleyelim
     * - Bize bir token üretip bu token ı dönsün.
     * - Ayrıca sadece Status u ACTIVE olan kullanıcılar giriş yapabiliyor olsun.
     *
     * 6-
     * -> AuthMicroService te oluşturduğumuz yapıyı; UserMicroService le bağlayalım ve gerekli güncellemeleri
     * AuthMicroService te gerçekleştirelim
     *  - AuthMicroService içinde manager.UserManager sınıfını oluşturduk
     *  - AuthMicroService ihtiyaç duyulan UserMicroService teki DTO sınıflarını copyalayıp AuthMicroService yapıştırıyoruz
     *  - AuthService te UserManager sınıfını tanımlıyoruz
     *  - Register methodunu revize ediyoruz
     *  - AuthMapper da @Mapping(source = "id", target = "authId") anatasyonunu tanımlayabileceğimiz bir
     * UserCreateRequestDto fromAuthToUserCreateRequestDto(Auth auth); methodu yazıyoruz
     *
     *  7-
     *  -> AuthMicroService te activeStatus işlemini gerçekleştirdiğimizde, UserMicroService tarafıda bu güncellemeyi
     *  alıp Status u değiştirsin.
     *  - AuthService teki activateStatus methodunda UserProfileService e istek atılacak -> Bunun için UserProfileController da
     *  activateStatus methodu yazılacak
     *
     *  8-
     * -> UserMicroService te UserProfile için bir update metodu yazalım.
     * - Dto içerisine token alacak. Bu token'ın nereden geleceğini iyi düşünün (Token'ın otomatik şekilde parametre
     * olarak geçilmesine gerek yok, bir token elde edin, bu token'ı dto'ya parametre olarak swagger'da işleyin)
     * Update edilecek UserProfile'ı token ile yakalayalım ve update işlemlerini gerçekleştirelim.
     *
     *  9-
     *  -> UserProfile ve Auth entitylerinde ortak olan ve veri bütünlüğünü bozan fieldlar username ve email
     *  iki entity arasındaki update i gerçekleştirmek için UpdateEmailOrUsernameRequestDto oluşturmamız gerekiyor
     *
     *
     */

}
# CoffeeShop - Online Coffee Store (Spring Boot)

## Công nghệ
- Spring Boot 3
- Thymeleaf
- Spring Data JPA
- MySQL
- IntelliJ IDEA

## Cấu hình database

Tạo database trong MySQL Workbench:

```sql
CREATE DATABASE coffeeshop_db;
```

Sau đó chỉnh lại `spring.datasource.username` và `spring.datasource.password`
trong file `src/main/resources/application.properties` cho đúng với máy của bạn.

## Chạy project

```bash
mvn spring-boot:run
```

Hoặc chạy trực tiếp class `CoffeeShopApplication` trong IntelliJ.

## Các URL chính

- Trang chủ: `http://localhost:8080/`
- Giỏ hàng: `http://localhost:8080/cart`
- Đăng nhập: `http://localhost:8080/login`
- Đăng ký: `http://localhost:8080/register`
- Trang admin: `http://localhost:8080/admin`

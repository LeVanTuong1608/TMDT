# Fix Cart Add API Issue  

## Steps  

1. [x] Edit src/main/resources/data.sql: Add sample users with hashed password.  **DONE**
2. [ ] Restart app (mvn spring-boot:run) to reload data.  
3. [ ] Login POST /api/auth/login để lấy JWT token.  
4. [ ] Test POST /api/users/cart/add?bookId=1&quantity=2 với header `Authorization: Bearer {token}`.  
5. [ ] Verify GET /api/users/cart (với token).  
6. [ ] [DONE] Test thành công.

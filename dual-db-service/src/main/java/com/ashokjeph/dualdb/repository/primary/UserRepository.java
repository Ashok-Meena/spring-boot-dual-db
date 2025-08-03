package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//@RepositoryRestResource(exported=false,collectionResourceRel = "users", path = "users")
//public interface UserRepository extends JpaRepository<User, String> {
//
//        @Query(value = """
//        SELECT * FROM dbo.app_user
//        WHERE LOWER(TRIM(email)) = LOWER(TRIM(?1))
//        """, nativeQuery = true)
//        Optional<User> findByEmailNative(String email);
//
//        @Query("""
//        SELECT u FROM User u
//        WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(:email))
//        """)
//        Optional<User> findByEmailIgnoreCase(@Param("email") String email);
//
//        // Add default method that tries both approaches
//        default Optional<User> findUserByEmail(String email) {
//            String processedEmail = email.trim().toLowerCase();
//            return findByEmailNative(processedEmail)
//                    .or(() -> findByEmailIgnoreCase(processedEmail));
//        }
//}
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = """
        SELECT * FROM dbo.app_user 
        WHERE LOWER(TRIM(email)) = LOWER(TRIM(?1))
        """, nativeQuery = true)
    Optional<User> findByEmailNative(String email);

    @Query("""
        SELECT u FROM User u 
        WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(:email))
        """)
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);

    default Optional<User> findUserByEmail(String email) {
        String processedEmail = email == null ? null : email.trim().toLowerCase();
        if (processedEmail == null || processedEmail.isBlank()) return Optional.empty();

        return findByEmailNative(processedEmail)
                .or(() -> findByEmailIgnoreCase(processedEmail));
    }
}
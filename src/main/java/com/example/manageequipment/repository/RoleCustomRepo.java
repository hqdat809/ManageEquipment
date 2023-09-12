package com.example.manageequipment.repository;

import com.example.manageequipment.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleCustomRepo extends JpaRepository<Role, Integer> {
    @Query("SELECT u.roles FROM User u WHERE u.email = :email")
    Set<Role> findRoleByEmail(@Param("email") String email);

    Optional<Role> findByName(String name);


//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Role> getRole(user user) {
//        StringBuilder sql = new StringBuilder()
//                .append("select r.name as name from user s join user_role sr on s.id = sr.user_id\n" +
//                        "join roles r on r.id = sr.role_id");
//        sql.append("where 1=1 ");
//        if (user.getEmail() != null) {
//            sql.append(" and email = :email");
//        }
//
//        NativeQuery<Role> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());
//
//        if(user.getEmail() != null) {
//            query.setParameter("email", user.getEmail());
//        }
//
//        query.addScalar("name", StandardBasicTypes.STRING);
//        query.setResultTransformer(Transformers.aliasToBean(Role.class));
//
//        return query.list();
//    }
}

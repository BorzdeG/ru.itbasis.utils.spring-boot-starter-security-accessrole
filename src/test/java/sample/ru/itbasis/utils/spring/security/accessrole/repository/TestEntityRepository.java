package sample.ru.itbasis.utils.spring.security.accessrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.ru.itbasis.utils.spring.security.accessrole.entity.TestEntity;

@Repository("testEntityRepository")
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
//	public TestEntityRepository(Class<TestEntity> domainClass, EntityManager em) {
//		super(domainClass, em);
//	}
}

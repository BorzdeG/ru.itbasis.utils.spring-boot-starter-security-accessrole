package sample.ru.itbasis.utils.spring.security.accessrole.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "test_entity")
@EqualsAndHashCode(callSuper = true)
public class TestEntity extends AbstractPersistable<Long> {
	@Column(name = "role")
	private IAccessRole role;

	@Column(name = "roles")
	private IAccessRole[] roles;

	@ElementCollection
	private List<IAccessRole> roleList;

	@ElementCollection
	private Set<IAccessRole> roleSet;
}

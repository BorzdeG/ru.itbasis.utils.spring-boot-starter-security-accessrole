package sample.ru.itbasis.utils.spring.security.accessrole.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.itbasis.utils.spring.security.accessrole.IAccessRole;

import javax.persistence.*;
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

	@ElementCollection(fetch = FetchType.EAGER)
	private List<IAccessRole> roleList;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<IAccessRole> roleSet;
}

package offside.stadium.repository;

import offside.stadium.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StadiumRepository extends JpaRepository<Stadium, Integer> { }

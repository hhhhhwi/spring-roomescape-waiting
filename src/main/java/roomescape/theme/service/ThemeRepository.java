package roomescape.theme.service;

import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.theme.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

}

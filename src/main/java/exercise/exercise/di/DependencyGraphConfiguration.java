package exercise.exercise.di;

import exercise.exercise.datasource.service.GameCrudRepository;
import exercise.exercise.datasource.service.GameRepository;
import exercise.exercise.datasource.service.UserCrudRepository;
import exercise.exercise.datasource.service.UserRepository;
import exercise.exercise.domain.service.TicTacToeService;
import exercise.exercise.domain.service.TicTacToeServiceImpl;
import exercise.exercise.domain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * Конфигурация для управления зависимостями в приложении.
 * Определяет, как создаются и связываются различные сервисы и репозитории.
 */
@Configuration
@ComponentScan(basePackages = "exercise.exercise")
public class DependencyGraphConfiguration {

	private UserService userService;
	private TicTacToeService ticTacToeService;
	private GameRepository gameRepository;
	private UserRepository userRepository;
	/**
	 * Конструктор, который инициализирует зависимости.
	 *
	 * @param userService сервис для работы с пользователями
	 * @param gameCrudRepository репозиторий для работы с сущностями игры
	 * @param userCrudRepository репозиторий для работы с сущностями пользователей
	 */
	@Autowired
	public DependencyGraphConfiguration(UserService userService,
										GameCrudRepository gameCrudRepository,
										UserCrudRepository userCrudRepository) {
		this.userService = userService;
		this.ticTacToeService = new TicTacToeServiceImpl(userService);
		this.gameRepository = new GameRepository(gameCrudRepository);
		this.userRepository = new UserRepository(userCrudRepository);
	}
	/**
	 * Получает экземпляр репозитория пользователей.
	 *
	 * @return экземпляр UserRepository
	 */
	public UserRepository userRepository() {
		return userRepository;
	}
	/**
	 * Получает экземпляр сервиса пользователей.
	 *
	 * @return экземпляр UserService
	 */
	public UserService userService() {
		return userService;
	}
	/**
	 * Получает экземпляр репозитория игр.
	 *
	 * @return экземпляр GameRepository
	 */
	public GameRepository gameRepositoryService() {
		return gameRepository;
	}
	/**
	 * Получает экземпляр сервиса игры в крестики-нолики.
	 *
	 * @return экземпляр TicTacToeService
	 */
	public TicTacToeService ticTacToeService() {
		return ticTacToeService;
	}
}
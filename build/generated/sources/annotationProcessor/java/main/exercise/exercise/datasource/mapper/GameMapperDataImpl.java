package exercise.exercise.datasource.mapper;

import exercise.exercise.datasource.model.GameEntity;
import exercise.exercise.domain.model.Game;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T09:57:01+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 18.0.2-ea (Private Build)"
)
public class GameMapperDataImpl implements GameMapperData {

    @Override
    public Game fromData(GameEntity gameEntity) {
        if ( gameEntity == null ) {
            return null;
        }

        Game game = new Game();

        if ( gameEntity.getGameId() != null ) {
            game.setGameId( UUID.fromString( gameEntity.getGameId() ) );
        }
        game.setBoard( map( gameEntity.getBoard() ) );
        game.setGameOver( gameEntity.isGameOver() );
        game.setSinglePlayer( gameEntity.isSinglePlayer() );
        game.setPlayer1( gameEntity.getPlayer1() );
        game.setPlayer2( gameEntity.getPlayer2() );
        game.setCurrentPlayer( gameEntity.getCurrentPlayer() );

        return game;
    }

    @Override
    public GameEntity toData(Game game) {
        if ( game == null ) {
            return null;
        }

        GameEntity gameEntity = new GameEntity();

        gameEntity.setBoard( map( game.getBoard() ) );
        if ( game.getGameId() != null ) {
            gameEntity.setGameId( game.getGameId().toString() );
        }
        gameEntity.setPlayer1( game.getPlayer1() );
        gameEntity.setPlayer2( game.getPlayer2() );
        gameEntity.setGameOver( game.isGameOver() );
        gameEntity.setCurrentPlayer( game.getCurrentPlayer() );
        gameEntity.setSinglePlayer( game.isSinglePlayer() );

        return gameEntity;
    }
}

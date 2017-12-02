package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Vector;

abstract public class GameEntity {
	private Entity entity;
	private ActorGame game;
	private int ID;

	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		this.game = game;
		EntityBuilder eBuilder = this.game.getEntityBuilder();
		eBuilder.setPosition(position);
		eBuilder.setFixed(fixed);
		entity = eBuilder.build();
	}
	public GameEntity(ActorGame game, boolean fixed) {
		this(game, fixed, Vector.ZERO);
	}
	public GameEntity(ActorGame game) {
		this(game, true, Vector.ZERO);
	}
	protected Entity getEntity() {
		return entity;
	}
	protected ActorGame getActorGame() {
		return game;
	}
	public void destroy() {
		entity.destroy();
	}
}

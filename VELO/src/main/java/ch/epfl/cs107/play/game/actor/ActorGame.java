package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

abstract public class ActorGame implements Game {
	// Viewport properties
	private Vector viewCenter ;
	private Vector viewTarget ;
	private Positionable viewCandidate ;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION =0.2f ;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND =0.1f ;
	private static final float VIEW_SCALE = 10.0f ;
	
	private Actor mainActor;
	private ArrayList<Actor> actors;
	private int IDCounter = 0;
	
	private World world;
	private Window window;
	private FileSystem fileSystem;
	private EntityBuilder eBuilder;
	
	public EntityBuilder getEntityBuilder() {
		return eBuilder;
	}
	public int getNewID() {
		IDCounter++;
		return IDCounter;
	}
	public Keyboard getKeyboard(){
		return window.getKeyboard() ;
	}
	public Canvas getCanvas(){
		return window ;
	}
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	public void deleteActor(Actor actorToDelete) {
		for(int i=0;i<actors.size();++i) {
			if(actors.get(i).equals(actorToDelete)) {
				actors.remove(i);
			}
		}
	}
	public void setViewCandidate(Positionable positionable) {
		viewCandidate.equals(positionable);
	}
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		// TODO Auto-generated method stub
		this.window = window;
		world = new World();
		viewCenter=Vector.ZERO;
		viewTarget=Vector.ZERO;
		eBuilder = world.createEntityBuilder();
		return true;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		world.update(deltaTime);
		mainActor.update(deltaTime);
		
		// Update expected viewport center
		if (viewCandidate != null) {
		viewTarget =
		viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION)) ;
		}
		// Interpolate with previous location
		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND ,deltaTime) ;
		viewCenter = viewCenter.mixed(viewTarget , 1.0f - ratio) ;
		// Compute new viewport
		Transform viewTransform =
		Transform.I.scaled(VIEW_SCALE).translated(viewCenter) ;
		window.setRelativeTransform(viewTransform) ;
		
		mainActor.draw(window);
		for(int i=1;i<actors.size();++i) {
			actors.get(i).update(deltaTime);
			actors.get(i).draw(window);
		}
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
	
}

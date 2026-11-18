/**
 * Lead Author(s):
 * 
 * @author calvinfronda; student ID
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         <<Add more references here>>
 * 		
 *         Version: 2026-11-10
 */
package physicstutor.physics;

/**
 * Purpose: The reponsibility of Particle is to manage the state of a single particle  
 *
 */
public class Particle extends PhysicsObject
{ 
    private final Vector2D startPosition;

    public Particle(double mass, Vector2D position) {
        super(mass, position);
        this.startPosition = position;
    }

    @Override
    public void update(double dt) {
        acceleration = getNetForce().scale(1.0 / mass);
        velocity = velocity.add(acceleration.scale(dt));
        position = position.add(velocity.scale(dt));
    }

    /** Put the particle back where it started, at rest. */
    public void reset() {
        position = startPosition;
        velocity = Vector2D.ZERO;
        acceleration = Vector2D.ZERO;
        clearForces();
    }
}

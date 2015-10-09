package point;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Point implements Serializable {
	@Id @GeneratedValue
	private long id;
	private int x;
	private int y;
	
        public Point() {
            
        }
        
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", this.x, this.y);
	}
	
	public void setX(int x) {
		this.x = x;		
	}
	
	public void setY(int y) {
		this.y = y;
	}
}

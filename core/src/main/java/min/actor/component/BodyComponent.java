package min.actor.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import min.actor.IBasicActor;

import java.util.Comparator;

public class BodyComponent {
    public World world;
    public Body body;
    Array<Object> contactObjList;

    public BodyComponent(IBasicActor actor, Body body, Filter filter){
        this.body = body;
        body.getFixtureList().get(0).setUserData(actor);
        body.getFixtureList().get(0).setFilterData(filter);
        world = body.getWorld();
        contactObjList = new Array<>();
    }

    public void setActive(boolean flag){
        if (body != null) {
            body.setActive(flag);
        }
    }
    public Vector2 getPosition(){
        return body.getPosition();
    }
    public void setPosition(Vector2 pos){
        body.setTransform(pos.x,pos.y,0);
    }

    public Object[] getContactActors(){
        contactObjList.clear();
        Array<Contact> contacts = world.getContactList();
        Fixture fixtureOwn = body.getFixtureList().get(0);
        for(Contact contact:contacts) {
            Object obj = getOpponentUserData(fixtureOwn,contact.getFixtureA(),contact.getFixtureB());
            if (obj != null)
                contactObjList.add(obj);
        }
        contactObjList.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                float dist1 = body.getPosition().dst2(((Actor)o1).getX(),((Actor)o1).getY());
                float dist2 = body.getPosition().dst2(((Actor)o2).getX(),((Actor)o2).getY());
                return (int)(dist1-dist2);
            }
        });
        return contactObjList.toArray();
    }

    public Object getOpponentUserData(Fixture fixtureOwn, Fixture fixtureA, Fixture fixtureB){
        if (fixtureOwn == fixtureA)
            return fixtureB.getUserData();
        else if (fixtureOwn == fixtureB)
            return fixtureA.getUserData();
        else
            return null;
    }

    public void detachBody(){
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }

    public void movePosition(float x, float y) {
        if (body != null) {
            body.setTransform(body.getWorldCenter().x + x,body.getWorldCenter().y + y, 0);
        }
    }

    public void initBody(Body body) {
        this.body = body;
    }

    /*
    @Override
    public boolean remove(){
        detachBody();
        return super.remove();
    }
    */
}

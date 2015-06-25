/*
 * User: Rich
 * Date: 6/25/2015
 * Time: 9:17 AM
*
 * The Chain of Responsibility Pattern.
 *
 * Gives the ability to chain object calls together.
 *
 * Allows for the client to make a request without having to know how that request is going to be handled.
 *
 */
package chainofresponsibility;


public class ChainOfResponsibility {
       
    static class HomeStatus {
        public boolean alarmOn = false;
        public boolean locked = true;
        public boolean lightsOff = false;
    }
        
    static abstract class HomeChecker{
        
        protected HomeChecker successor;
        
        abstract public void check(HomeStatus home);
        
        public void succeedWith(HomeChecker successor)
        {
            this.successor = successor;
        }
        
        public void next(HomeStatus home)
        {
            if (this.successor != null)
            {
                this.successor.check(home);
            }
        }
    }
    
    static class Locks extends HomeChecker {
        
        @Override
        public void check(HomeStatus home)
        {
            if(!home.locked)
            {
                System.out.println("The doors are not locked!! Abort abort.");
            }
            
            this.next(home);
        }
    }
    
    static class Lights extends HomeChecker {
        
        @Override
        public void check(HomeStatus home)
        {
            if(!home.lightsOff)
            {
                System.out.println("The lights are still on!! Abort abort.");
            }
            
            this.next(home);
        }
    }
    
    static class Alarm extends HomeChecker {
        
        @Override
        public void check(HomeStatus home)
        {
            if(!home.alarmOn)
            {
                System.out.println("he alarm has not been set!! Abort abort.");
            }
            
            this.next(home);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // create new instances
        Locks locks = new Locks();
        Lights lights = new Lights();
        Alarm alarm = new Alarm();
        
        // set the successor chain
        locks.succeedWith(lights);
        lights.succeedWith(alarm);
        
        // start off here...
        locks.check(new HomeStatus());
        
    }
    
}

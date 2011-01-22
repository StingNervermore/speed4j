package com.ecyrd.zoom4j;

import java.io.Serializable;

/**
 *  Creates a simple StopWatch with nanosecond precision (though not necessarily accuracy).
 *  <p>
 *  A "tag" is an unique grouping identifier. A "message" can be anything you like; it just
 *  travels with the StopWatch and is output with the toString method.
 */
public class StopWatch implements Serializable
{
    private static final long serialVersionUID = 5154481161113185022L;

    private long   m_startNanos;
    private long   m_stopNanos;
    private String m_tag = "?";
    private String m_message;
        
    private static final long NANOS_IN_SECOND = 1000*1000*1000;
    
    protected StopWatch( String tag, String message )
    {
        m_tag = tag;
        m_message = message;
        start();
    }
    
    public StopWatch start()
    {
        m_startNanos = System.nanoTime();
        
        return this;
    }
    
    protected void internalStop()
    {
        m_stopNanos = System.nanoTime();
    }
    
    public StopWatch stop()
    {
        internalStop();
        
        return this;
    }
    
    public StopWatch stop( String tag )
    {
        m_tag = tag;
        stop();
        
        return this;
    }
    
    public StopWatch stop( String tag, String message )
    {
        m_tag = tag;
        m_message = message;
        stop();
        
        return this;
    }
    
    public StopWatch lap()
    {
        stop();
        start();
        
        return this;
    }
    
    public String getMessage()
    {
        return m_message;
    }
    
    public String getTag()
    {
        return m_tag;
    }
    
    /**
     *  Returns the elapsed time in nanoseconds.
     *  
     *  @return
     */
    public long getTimeNanos()
    {
        if( m_stopNanos != 0 )
            return m_stopNanos - m_startNanos;
        
        return System.nanoTime() - m_startNanos;
    }
    
    /**
     *  Returns a human-readable string.  This is a slowish op, so don't call unnecessarily.
     *  Do NOT rely this in being any particular format.
     */
    public String toString()
    {
        return m_tag+": "+getReadableTime() + (m_message != null ? m_message : "");
    }

    /**
     *  Returns a human readable string which also calculates the speed of a single
     *  operation.  Do NOT rely on this being in any particular format. For example:
     *  
     *  <pre>
     *    StopWatch sw = ...
     *    for( int i = 0; i < 1000; i++ )
     *    {
     *       // Do something
     *    }
     *    sw.stop("test");
     *    System.out.println( sw.toString(1000) );
     *  </pre>
     *  This might print out something like:
     *  <pre>
     *    test: 14520 ms (68 iterations/second)
     *  </pre>
     *  
     *  @param iterations
     *  @return
     */
    public String toString( int iterations )
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( m_tag );
        sb.append( ": " );
        sb.append( getReadableTime() );
        if( m_message != null ) sb.append(" "+m_message);
        sb.append( " ("+iterations * NANOS_IN_SECOND / getTimeNanos()+" iterations/second)");
        
        return sb.toString();
    }
    
    /**
     *  Returns a the time in something that is human-readable.
     *  
     *  @return A human-readable time string.
     */
    private String getReadableTime()
    {
        long ns = getTimeNanos();
        
        if( ns < 50L * 1000 )
            return ns + " ns";
       
        if( ns < 50L * 1000 * 1000 )
            return (ns/1000)+" us";
        
        if( ns < 50L * 1000 * 1000 * 1000 )
            return (ns/(1000*1000))+" ms";
        
        return ns/NANOS_IN_SECOND + " s";
    }
    
    /**
     *  Returns a cloned, freezed copy of the StopWatch.  The returned StopWatch is
     *  automatically stopped.
     *  
     *  @return
     */
    // TODO: Should probably return a FrozenStopWatch
    protected StopWatch freeze()
    {
        StopWatch sw = new StopWatch( m_tag, m_message );
        sw.m_startNanos = m_startNanos;
        sw.m_stopNanos = m_stopNanos != 0 ? m_stopNanos : System.nanoTime();
        
        return sw;
    }
}

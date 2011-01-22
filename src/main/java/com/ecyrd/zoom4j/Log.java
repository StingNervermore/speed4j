package com.ecyrd.zoom4j;

/**
 *  Base class for the Logs.
 *  
 */
public abstract class Log
{
    private boolean m_enable = true;
    
    public void setEnable(String value)
    {
        if( value.equals("false") ) m_enable = false;
        else m_enable = true;
    }
    
    public boolean isEnabled()
    {
        return m_enable;
    }
                             
    
    public abstract void log(StopWatch sw);

    /**
     *  Shuts the Log down.  This can be used to free resources, etc.
     */
    public void shutdown()
    {
        // Default implementation does nothing
    }

}

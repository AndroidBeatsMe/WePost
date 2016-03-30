package com.nancyberry.wepost.common.network;

import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Holder class. This is usually used for passing data from callback function to outside closure.
 *
 * @author tao.li
 * @version %I%, %G%
 */
public final class Holder<DataT> implements Future<DataT> {

    private static final String TAG = Holder.class.getSimpleName();

    private volatile boolean isNull = true;

    private DataT value;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        synchronized (this) {
            return !isNull;
        }
    }

    /**
     * Return the data to the user
     *
     * @return value which is holden by Holder
     */
    public DataT get() {
        try {
            synchronized (this) {
                if (isNull) {
                    this.wait();
                }
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException happened in Holder DataT: " + e.getMessage());
            return null;
        }
        return value;
    }

    @Override
    public DataT get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (isNull) {
                this.wait(unit.toMillis(timeout));
                if (isNull) {
                    throw new TimeoutException();
                }
            }
        }
        return value;
    }

    /**
     * Set the value to the holder
     *
     * @param v value
     */
    public void set(DataT v) {
        value = v;
        synchronized (this) {
            isNull = false;
            this.notifyAll();
        }
    }


}

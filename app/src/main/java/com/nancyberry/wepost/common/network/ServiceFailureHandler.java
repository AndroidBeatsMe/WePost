package com.nancyberry.wepost.common.network;

/**
 * TODO add description here
 *
 * @version %I%, %G%
 */
public interface ServiceFailureHandler {

    boolean onFailure(Exception e);
}

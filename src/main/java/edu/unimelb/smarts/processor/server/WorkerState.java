package edu.unimelb.smarts.processor.server;

public enum WorkerState {
	NEW, READY, SHARING_STARTED, SHARED, SIMULATING, FINISHED_ONE_STEP, SERVERLESS_WORKING
}

package org.basicprogramming.loaders;

public class ProgressBar implements ProgressListener {
    private volatile int current;
    public volatile int max;

    @Override
    public void update(int current, int max) {
        this.current = current;
        this.max = max;
    }

    public String getProgress() {
        return String.format("%d / %d", current, max);
    }

    public double getPercent() {
        if (max == 0) {
            return 0;
        }
        return (double)current / max;
    }

    public int getCurrent() {
        return current;
    }
}

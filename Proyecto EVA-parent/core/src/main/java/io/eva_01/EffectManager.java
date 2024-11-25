package io.eva_01;

import java.util.HashMap;
import java.util.Map;

public class EffectManager {
    public enum EffectType {
        SHIELD, DOUBLE_SHOT, SPEED_BOOST, SPEED_DECREASE;
    }

    private class Effect {
        float duration;
        boolean active;

        Effect(float duration) {
            this.duration = duration;
            this.active = true;
        }

        void update(float delta) {
            if (active) {
                duration -= delta;
                if (duration <= 0) {
                    active = false;
                }
            }
        }

        boolean isActive() {
            return active;
        }
    }

    private Map<EffectType, Effect> activeEffects;

    public EffectManager() {
        this.activeEffects = new HashMap<>();
    }

    public void activateEffect(EffectType type, float duration) {
        activeEffects.put(type, new Effect(duration));
    }

    public void updateEffects(float delta) {
        for (Effect effect : activeEffects.values()) {
            effect.update(delta);
        }
    }

    public boolean isEffectActive(EffectType type) {
        Effect effect = activeEffects.get(type);
        return effect != null && effect.isActive();
    }
}


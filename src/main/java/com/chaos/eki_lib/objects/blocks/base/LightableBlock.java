package com.chaos.eki_lib.objects.blocks.base;

public class LightableBlock extends BaseBlock {
    public LightableBlock(Properties properties, int light) {
        super(setLightLevel(properties, light));
    }

    private static Properties setLightLevel(Properties p, int l) {
        return p.setLightLevel(s -> l);
    }

    public class LightableHorizontalBlock extends HorizontalBaseBlock {
        public LightableHorizontalBlock(Properties properties, int light) {
            super(setLightLevel(properties, light));
        }
    }

    public class LightableDirectionalBlock extends DirectionalBaseBlock {
        public LightableDirectionalBlock(Properties properties, int light) {
            super(setLightLevel(properties, light));
        }
    }
}

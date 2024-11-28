package com.climinby.starsky_e.client.entity;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.client.entity.model.LunarianEntityModel;
import com.climinby.starsky_e.entity.LunarianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LunarianEntityRenderer extends MobEntityRenderer<LunarianEntity, LunarianEntityModel> {

    public LunarianEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LunarianEntityModel(), 0.4F);
    }

    @Override
    public Identifier getTexture(LunarianEntity entity) {
        return new Identifier(StarSkyExplority.MOD_ID, "textures/entity/lunarian/lunarian.png");
    }
}

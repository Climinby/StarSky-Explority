package com.climinby.starsky_e.client.entity;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.client.entity.model.TsukiNoTamiEntityModel;
import com.climinby.starsky_e.entity.TsukiNoTamiEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TsukiNoTamiEntityRenderer extends MobEntityRenderer<TsukiNoTamiEntity, TsukiNoTamiEntityModel> {

    public TsukiNoTamiEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TsukiNoTamiEntityModel(), 0.4F);
    }

    @Override
    public Identifier getTexture(TsukiNoTamiEntity entity) {
        return new Identifier(StarSkyExplority.MOD_ID, "textures/entity/tsuki_no_tami/tsuki_no_tami.png");
    }
}

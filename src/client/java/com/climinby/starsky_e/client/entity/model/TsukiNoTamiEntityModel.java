package com.climinby.starsky_e.client.entity.model;

import com.climinby.starsky_e.entity.TsukiNoTamiEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class TsukiNoTamiEntityModel extends EntityModel<TsukiNoTamiEntity> {
    private final ModelPart legs;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart ears;
    private final ModelPart left_hand;
    private final ModelPart right_hand;
    private final ModelPart bb_main;

    public TsukiNoTamiEntityModel() {
        this(getTexturedModelData().createModel());
    }
    public TsukiNoTamiEntityModel(ModelPart root) {
        this.legs = root.getChild("legs");
        this.head = root.getChild("head");
        this.hat = head.getChild("hat");
        this.ears = head.getChild("ears");
        this.left_hand = root.getChild("left_hand");
        this.right_hand = root.getChild("right_hand");
        this.bb_main = root.getChild("bb_main");
    }

    @Override
    public void setAngles(TsukiNoTamiEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.hurtTime > 0) {
            playHurtAnimation((float) (entity.hurtTime) / 10.0F * (float) (Math.PI / 3.0));
            this.head.yaw = headYaw;
            this.head.pitch = headPitch;
        } else {
            this.left_hand.pitch = 0F;
            this.left_hand.pivotY = 24F;
            this.left_hand.pivotZ = 0F;
            this.right_hand.pitch = 0F;
            this.right_hand.pivotY = 24F;
            this.right_hand.pivotZ = 0F;
            this.head.yaw = headYaw;
            this.head.pitch = headPitch;
        }
    }

    private void playHurtAnimation(float animationProgress) {
        this.left_hand.pitch = animationProgress;
        this.left_hand.pivotY = 24.0F * ((float) Math.cos(animationProgress) - 1.0F) + 24.0F;
        this.left_hand.pivotZ = 24.0F * (float) Math.sin(animationProgress);
        this.right_hand.pitch = -animationProgress;
        this.right_hand.pivotY = 24.0F * ((float) Math.cos(animationProgress) - 1.0F) + 24.0F;
        this.right_hand.pivotZ = - (24.0F * (float) Math.sin(animationProgress));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        legs.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        left_hand.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        right_hand.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        bb_main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData legs = modelPartData.addChild("legs", ModelPartBuilder.create().uv(0, 29).cuboid(2.0F, 11.0F, -5.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(28, 0).cuboid(-2.0F, 11.0F, -5.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 1.0F, 3.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -9.0F, -7.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 1.0F, 3.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData hat_r1 = hat.addChild("hat_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -10.0F, -4.0F, 9.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_ear_r1 = ears.addChild("right_ear_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, 1.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -8.0F, -6.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData left_ear_r1 = ears.addChild("left_ear_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-1.0F, -5.0F, 1.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, -8.0F, -6.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData left_hand = modelPartData.addChild("left_hand", ModelPartBuilder.create().uv(26, 41).cuboid(4.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_hand = modelPartData.addChild("right_hand", ModelPartBuilder.create().uv(12, 41).cuboid(-7.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(24, 25).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}

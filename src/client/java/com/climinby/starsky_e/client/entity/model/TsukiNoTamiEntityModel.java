package com.climinby.starsky_e.client.entity.model;

import com.climinby.starsky_e.entity.TsukiNoTamiEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class TsukiNoTamiEntityModel extends EntityModel<TsukiNoTamiEntity> {
    private final ModelPart main;
    private final ModelPart right_hand;
    private final ModelPart left_hand;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart ears;
    private final ModelPart ohead;
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public TsukiNoTamiEntityModel() {
        this(getTexturedModelData().createModel());
    }
    public TsukiNoTamiEntityModel(ModelPart root) {
        this.main = root.getChild("main");
        this.right_hand = this.main.getChild("right_hand");
        this.left_hand = this.main.getChild("left_hand");
        this.head = this.main.getChild("head");
        this.hat = this.head.getChild("hat");
        this.ears = this.head.getChild("ears");
        this.ohead = this.head.getChild("ohead");
        this.body = this.main.getChild("body");
        this.left_leg = this.main.getChild("left_leg");
        this.right_leg = this.main.getChild("right_leg");
    }

    @Override
    public void setAngles(TsukiNoTamiEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.hurtTime > 0) {
            playHurtAnimation((float) (entity.hurtTime) / 10.0F * (float) (Math.PI / 3.0));
            this.head.yaw = headYaw;
            this.head.pitch = headPitch;
        } else {
            this.left_hand.pitch = 0F;
            this.left_hand.pivotY = 0F;
            this.left_hand.pivotZ = 0F;
            this.right_hand.pitch = 0F;
            this.right_hand.pivotY = 0F;
            this.right_hand.pivotZ = 0F;

            this.right_leg.pitch = 0F;
            this.right_leg.pivotY = 0F;
            this.right_leg.pivotZ = 0F;
            this.left_leg.pitch = 0F;
            this.left_leg.pivotY = -6.0F;
            this.left_leg.pivotZ = 0F;
            this.head.yaw = headYaw;
            this.head.pitch = headPitch;
        }
    }

    /**
     * @param rotationAngle a number to be in radians
     */
    private void playHurtAnimation(float rotationAngle) {
        this.left_hand.pitch = rotationAngle;
        this.left_hand.pivotY = 23.0F * ((float) Math.cos(rotationAngle) - 1.0F);
        this.left_hand.pivotZ = 23.0F * (float) Math.sin(rotationAngle);
        this.right_hand.pitch = -rotationAngle;
        this.right_hand.pivotY = 23.0F * ((float) Math.cos(rotationAngle) - 1.0F);
        this.right_hand.pivotZ = - (23.0F * (float) Math.sin(rotationAngle));
        this.left_leg.pitch = -rotationAngle;
        this.left_leg.pivotY = 6.0F * ((float) Math.cos(rotationAngle) - 1.0F) - 6.0F;
        this.left_leg.pivotZ = - (6.0F * (float) Math.sin(rotationAngle));
        this.right_leg.pitch = rotationAngle;
        this.right_leg.pivotY = 12.0F * ((float) Math.cos(rotationAngle) - 1.0F);
        this.right_leg.pivotZ = 12.0F * (float) Math.sin(rotationAngle);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData right_hand = main.addChild("right_hand", ModelPartBuilder.create().uv(12, 41).cuboid(-7.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_hand = main.addChild("left_hand", ModelPartBuilder.create().uv(26, 41).cuboid(4.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = main.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -23.0F, 3.0F));

        ModelPartData hat_r1 = hat.addChild("hat_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -10.0F, -4.0F, 9.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -23.0F, 3.0F));

        ModelPartData right_ear_r1 = ears.addChild("right_ear_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, 1.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -8.0F, -6.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData left_ear_r1 = ears.addChild("left_ear_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-1.0F, -5.0F, 1.0F, 2.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, -8.0F, -6.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData ohead = head.addChild("ohead", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -9.0F, -7.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -23.0F, 3.0F));

        ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(24, 25).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.0F, 0.0F));

        ModelPartData left_leg = main.addChild("left_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -6.0F, 0.0F));

        ModelPartData right_leg = main.addChild("right_leg", ModelPartBuilder.create().uv(28, 0).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}

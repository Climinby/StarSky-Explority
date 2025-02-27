package com.climinby.starsky_e.world.dimension;

import com.climinby.starsky_e.StarSkyExplority;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypeRegistrar;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class SSEDimensions {
    public static final Identifier THE_MOON_ID = new Identifier(StarSkyExplority.MOD_ID, "the_moon");

    public static final RegistryKey<DimensionOptions> THE_MOON_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION,
            THE_MOON_ID
    );

    public static final RegistryKey<World> THE_MOON_LEVEL_KEY = RegistryKey.of(
            RegistryKeys.WORLD,
            THE_MOON_ID
    );

    public static final RegistryKey<DimensionType> THE_MOON_TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE,
            THE_MOON_ID
    );

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(THE_MOON_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                false,
                1.0,
                true,
                true,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.OVERWORLD_ID,
                0.0F,
                new DimensionType.MonsterSettings(
                        true,
                        false,
                        ConstantIntProvider.create(7),
                        0
                )
        ));
    }
//    public static final DimensionType THE_MOON = new DimensionType(
//            OptionalLong.empty(),
//            true, // hasSkyLight
//            false, // hasCeiling
//            true, // ultrawarm
//            false, // natural
//            1.0, // coordinateScale
//            true, // bedWorks
//            true, // respawnAnchorWorks
//            -32, // minY
//            288, // height
//            288, // logicalHeight
//            TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "infiniburn_overworld")),
//            new Identifier("minecraft", "overworld"), // effects location
//            0.0f, // ambientLight
//            new DimensionType.MonsterSettings(false, false, ConstantIntProvider.create(7), 0)
//    );
//
//    public static void init() {
//
//    }
}

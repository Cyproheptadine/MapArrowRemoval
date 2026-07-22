package org.Ciptoprolol.maparrowremoval.mixin.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MapRenderer.class)
public class MapRendererMixin {
    private static final Identifier PLAYER = Identifier.withDefaultNamespace("player");
    private static final Identifier PLAYER_OFF_MAP = Identifier.withDefaultNamespace("player_off_map");
    private static final Identifier PLAYER_OFF_LIMITS = Identifier.withDefaultNamespace("player_off_limits");
    private static final Identifier FRAME = Identifier.withDefaultNamespace("frame");

    @Redirect(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;getDecorations()Ljava/lang/Iterable;"
            )
    )
    private Iterable<MapDecoration> maparrowremoval$hidePlayerLocatorDecorations(MapItemSavedData mapData) {
        List<MapDecoration> visibleDecorations = new ArrayList<>();

        for (MapDecoration decoration : mapData.getDecorations()) {
            if (!maparrowremoval$isPlayerLocatorDecoration(decoration)) {
                visibleDecorations.add(decoration);
            }
        }

        return visibleDecorations;
    }

    private static boolean maparrowremoval$isPlayerLocatorDecoration(MapDecoration decoration) {
        return decoration.type().is(PLAYER)
                || decoration.type().is(PLAYER_OFF_MAP)
                || decoration.type().is(PLAYER_OFF_LIMITS)
                || decoration.type().is(FRAME);
    }
}

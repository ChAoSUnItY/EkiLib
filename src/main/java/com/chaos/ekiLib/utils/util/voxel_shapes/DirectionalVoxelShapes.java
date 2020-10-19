package com.chaos.ekiLib.utils.util.voxel_shapes;

import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class DirectionalVoxelShapes {
    public final VoxelShape[] shapes = new VoxelShape[6];

    /***
     * @param shapesStream, the sequence to resolve is North -> South -> East -> West -> Up -> Down, otherwise it would return wrong VoxelShape when you call.
     */
    public DirectionalVoxelShapes(Stream<VoxelShape>... shapesStream) {
        for (int i = 0; i < 6; i++)
            this.shapes[i] = shapesStream[i].reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }

    public VoxelShape getNorth() {
        return this.shapes[0];
    }

    public VoxelShape getSouth() {
        return this.shapes[1];
    }

    public VoxelShape getEast() {
        return this.shapes[2];
    }

    public VoxelShape getWest() {
        return this.shapes[3];
    }

    public VoxelShape getUp() { return this.shapes[4]; }

    public VoxelShape getDown() { return this.shapes[5]; }
}

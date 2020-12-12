package com.chaos.eki_lib.utils.util.voxel_shapes;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Arrays;
import java.util.stream.Stream;

public class DirectionalVoxelShapes {
    public final VoxelShape[] shapes = new VoxelShape[6];

    /***
     * @param shapesStream, the sequence to resolve is North -> South -> East -> West -> Up -> Down, otherwise it would return wrong VoxelShape when you call.
     */
    public DirectionalVoxelShapes(Stream<VoxelShape>... shapesStream) {
        this(Arrays.stream(shapesStream)
                .map(stream ->
                        stream.reduce((v1, v2) ->
                                VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get())
                .toArray(VoxelShape[]::new));
    }

    public DirectionalVoxelShapes(VoxelShape... shapes) {
        if (shapes.length != 6)
            throw new IllegalStateException("DirectionalVoxelShapes only takes six Streams of VoxelShape");
        for (int i = 0; i < 6; i++)
            this.shapes[i] = shapes[i];
    }

    public VoxelShape getByDirection(Direction direction) {
        switch (direction) {
            case SOUTH:
                return getSouth();
            case EAST:
                return getEast();
            case WEST:
                return getWest();
            case UP:
                return getUp();
            case DOWN:
                return getDown();
            case NORTH:
            default:
                return getNorth();
        }
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

    public VoxelShape getUp() {
        return this.shapes[4];
    }

    public VoxelShape getDown() {
        return this.shapes[5];
    }
}

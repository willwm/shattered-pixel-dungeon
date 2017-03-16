/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Foliage;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.plants.BlandfruitBush;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.watabou.utils.Random;

public class GardenRoom extends Room {

	public void paint( Level level, Room room ) {
		
		Painter.fill( level, room, Terrain.WALL );
		Painter.fill( level, room, 1, Terrain.HIGH_GRASS );
		Painter.fill( level, room, 2, Terrain.GRASS );
		
		room.entrance().set( Room.Door.Type.REGULAR );

		if (Dungeon.isChallenged(Challenges.NO_FOOD)) {
			if (Random.Int(2) == 0){
				level.plant(new Sungrass.Seed(), level.pointToCell(room.random()));
			}
		} else {
			int bushes = Random.Int(3);
			if (bushes == 0) {
				level.plant(new Sungrass.Seed(), level.pointToCell(room.random()));
			} else if (bushes == 1) {
				level.plant(new BlandfruitBush.Seed(), level.pointToCell(room.random()));
			} else if (Random.Int(5) == 0) {
				int plant1, plant2;
				plant1 = level.pointToCell(room.random());
				level.plant(new Sungrass.Seed(), plant1);
				do {
					plant2 = level.pointToCell(room.random());
				} while (plant2 == plant1);
				level.plant(new BlandfruitBush.Seed(), plant2);
			}
		}
		
		Foliage light = (Foliage)level.blobs.get( Foliage.class );
		if (light == null) {
			light = new Foliage();
		}
		for (int i=room.top + 1; i < room.bottom; i++) {
			for (int j=room.left + 1; j < room.right; j++) {
				light.seed( level, j + level.width() * i, 1 );
			}
		}
		level.blobs.put( Foliage.class, light );
	}
}

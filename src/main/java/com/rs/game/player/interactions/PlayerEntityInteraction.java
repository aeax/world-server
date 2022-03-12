package com.rs.game.player.interactions;

import com.rs.game.Entity;
import com.rs.game.player.Player;
import com.rs.game.player.actions.interactions.EntityInteraction;

public abstract class PlayerEntityInteraction extends EntityInteraction {
	
	public PlayerEntityInteraction(Entity target, int distance) {
		super(target, distance);
	}
	
	public abstract boolean canStart(Player player);
	public abstract boolean checkAll(Player player);
	public abstract void interact(Player player);
	public abstract void onStop(Player player);

	@Override
	public boolean canStart(Entity entity) {
		if (!(entity instanceof Player player))
			return false;
		return canStart(player);
	}

	@Override
	public boolean checkAll(Entity entity) {
		if (!(entity instanceof Player player))
			return false;
		return checkAll(player);
	}

	@Override
	public void interact(Entity entity) {
		if (entity instanceof Player player)
			interact(player);
	}

	@Override
	public void onStop(Entity entity) {
		if (entity instanceof Player player)
			onStop(player);
	}

}

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.content.world.areas.draynor.npcs;

import com.rs.engine.dialogue.Dialogue;
import com.rs.engine.dialogue.HeadE;
import com.rs.game.model.entity.npc.NPC;
import com.rs.game.model.entity.player.Player;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.handlers.NPCClickHandler;

@PluginEventHandler
public class WiseOldMan {

	public static NPCClickHandler handleTalk = new NPCClickHandler(new Object[] { 3820 }, e -> {
		Player player = e.getPlayer();
		NPC npc = e.getNPC();

		Dialogue skillcape = new Dialogue()
				.addNPC(npc, HeadE.HAPPY_TALKING, "Greetings! What can I do for you?");
		if (player.getQuestManager().completedAllQuests()) {
			skillcape.addPlayer(HeadE.CONFUSED, "I was wondering if you could sell me a quest cape! I have completed all the quests.")
					.addNPC(npc, HeadE.LAUGH, "Impressive! I see you have! It will cost you 99,000 coins, though.")
					.addOption("Choose an option", "Yes, I have that with me now.", "Sorry, nevermind.");

			if (player.getInventory().hasCoins(99000)) {
				skillcape.addPlayer(HeadE.HAPPY_TALKING, "Yeah I have that with me. Here you go.")
						.addNPC(npc, HeadE.LAUGH, "Wear the cape with pride, adventurer.", () -> {
							if (player.getInventory().hasCoins(99000)) {
								player.getInventory().removeCoins(99000);
								player.getInventory().addItemDrop(9814, 1);
								player.getInventory().addItemDrop(9813, 1);
							}
						});
			}
		} else {
			skillcape.addPlayer(HeadE.CONFUSED, "I'm not sure. What can you do for me?")
					.addNPC(npc, HeadE.HAPPY_TALKING, "I can offer you a quest cape once you reach maximum quest points.");
		}

		player.startConversation(skillcape);
	});

}

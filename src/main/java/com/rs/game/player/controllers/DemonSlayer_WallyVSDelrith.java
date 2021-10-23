package com.rs.game.player.controllers;

import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.player.content.dialogue.HeadE;
import com.rs.game.player.quests.handlers.demonslayer.GypsyArisDemonSlayerD;
import com.rs.game.region.RegionBuilder.DynamicRegionReference;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.lib.game.Animation;
import com.rs.lib.game.WorldTile;

public class DemonSlayer_WallyVSDelrith extends Controller {
    static final int WALLY = 4664;
    static final int GYPSY_ARIS = 882;
    DynamicRegionReference instance;
    WorldTile locationBeforeCutscene;
    WorldTile spawn;

	@Override
	public void start() {
        playCutscene();
	}

	private void playCutscene() {
        instance = new DynamicRegionReference(64, 64);
        locationBeforeCutscene = new WorldTile(player.getX(), player.getY(), player.getPlane());
        player.lock();


        instance.copyMap(0, 0, 401, 419, 6, ()-> {
            spawn = instance.getLocalTile(19, 17);
            System.out.println(spawn);

            WorldTasksManager.schedule(new WorldTask() {
                int tick;
                NPC npc;
                @Override
                public void run() {
                    if (tick == 0) { //setup p1
                        player.getInterfaceManager().setFadingInterface(115);
                        player.getPackets().sendMusic(-1, 100, 255);
                    } else if (tick == 3) {//setup p2, move player
                        player.getPackets().setBlockMinimapState(2);
                        player.setNextWorldTile(spawn);
                        player.getAppearance().transformIntoNPC(266);
                    } else if (tick == 5) {//setup p3, camera
                        player.getPackets().sendCameraShake(1, 0, 10, 5, 10);
                        player.getPackets().sendCameraPos(player.getXInScene(player.getSceneBaseChunkId()), player.getYInScene(player.getSceneBaseChunkId()), 1300);
                        player.getPackets().sendCameraLook(player.getXInScene(player.getSceneBaseChunkId())+4, player.getYInScene(player.getSceneBaseChunkId())-4, 50);
                    } else if (tick == 6) {//start scene
                        player.getInterfaceManager().setFadingInterface(170);
                        player.getPackets().sendMusic(196, 100, 255);
                        npc = (NPC) World.spawnNPC(WALLY, new WorldTile(player.getX()-1, player.getY()-5, player.getPlane()), -1, false, true);
                        npc.setRandomWalk(false);
                    } else if(tick == 7) { //Gypsy Aris #1
                        player.startConversation(new Conversation(player) {
                            {
                                addNPC(GYPSY_ARIS, HeadE.TALKING_ALOT, "Wally managed to arrive at the stone circle just as Delrith was summoned by a cult of chaos" +
                                        " druids...");
                                addNext(() -> {
                                    tick++;
                                });
                                create();
                            }
                        });
                    }else if(tick == 8) { //Wally #1
                        npc.faceEntity(player);
                        npc.setRun(true);
                        npc.setForceWalk(new WorldTile(spawn.getX(), spawn.getY() - 2, spawn.getPlane()));
                        player.startConversation(new Conversation(player) {
                            {
                                addNPC(WALLY, HeadE.TALKING_ALOT, "Die foul demon!");
                                create();
                            }
                        });
                        player.getPackets().sendCameraLook(player.getXInScene(player.getSceneBaseChunkId()), player.getYInScene(player.getSceneBaseChunkId())-4, 0, 4, 4);
                    } else if(tick == 9) {
                        npc.faceEntity(player);
                        npc.setNextAnimation(new Animation(12311));
                    } else if(tick == 10) {
                        npc.setNextAnimation(new Animation(2394));
                    } else if(tick == 11) {
                        npc.setNextAnimation(new Animation(16290));
                        player.getPackets().sendCameraLook(player.getXInScene(player.getSceneBaseChunkId()), player.getYInScene(player.getSceneBaseChunkId())-3, 0, 1, 1);
                        player.getPackets().sendCameraPos(player.getXInScene(player.getSceneBaseChunkId()), player.getYInScene(player.getSceneBaseChunkId()), 250, 0, 40);
                    } else if(tick == 13) {
                        player.startConversation(new Conversation(player) { //Wally #2
                            {
                                addNPC(WALLY, HeadE.TALKING_ALOT, "Now, what was that incantation again?");
                                addNext(() -> {
                                    tick++;
                                });
                                create();
                            }
                        });
                    } else if(tick == 14) {
                        npc.faceEntity(player);
                        player.startConversation(new Conversation(player) { //Wally #2
                            {
                                addNPC(WALLY, HeadE.TALKING_ALOT, "Aber, Gabindo, Purchai, Camerinthum, and Carlem");
                                addNext(() -> {
                                    tick++;
                                });
                                create();
                            }
                        });
                    } else if (tick == 17) {//closing scene 1
                        player.getInterfaceManager().setFadingInterface(115);
                    } else if(tick == 20) {//setup scene 2
                        player.getPackets().sendCameraPos(player.getXInScene(player.getSceneBaseChunkId())-2, player.getYInScene(player.getSceneBaseChunkId())-5, 1450); //move cam scene 2
                        player.getPackets().sendCameraLook(player.getXInScene(player.getSceneBaseChunkId()), player.getYInScene(player.getSceneBaseChunkId())-2, 600);//face Wally
                    } else if(tick == 21) {
                        player.getInterfaceManager().removeInterface(115);
                    } else if(tick == 22) {
                        npc.faceTile(new WorldTile(spawn.getX()-1, spawn.getY()-3, 0));//face camera
                        npc.setNextAnimation(new Animation(12625));
                        player.startConversation(new Conversation(player) { //Wally #2
                            {
                                addNPC(WALLY, HeadE.TALKING_ALOT, "I am the greatest demon slayer EVER!");
                                addNext(() -> {
                                    tick++;
                                });
                                create();
                            }
                        });
                    } else if(tick == 23) {
                        player.startConversation(new Conversation(player) {
                            {
                                addNPC(GYPSY_ARIS, HeadE.TALKING_ALOT, "By reciting the correct magical incantation, and thrusting Silverlight into Delrith " +
                                        "while he was newly summoned, Wally was able to imprison Delrith in the stone block in the centre of the circle.");
                                addNext(() -> {
                                    player.getInterfaceManager().setFadingInterface(115);
                                    tick++;
                                });
                                create();
                            }
                        });
                    } else if (tick == 26) {//closing p1
                        player.getPackets().setBlockMinimapState(0);
                        player.getControllerManager().forceStop();
                        player.getAppearance().transformIntoNPC(-1);
                        player.getPackets().sendStopCameraShake();
                    } else if (tick == 27) {//closing p2
                        player.getInterfaceManager().setFadingInterface(170);
                        player.getPackets().sendMusic(125, 100, 255);
                        player.setTempB("DemonSlayerCutscenePlayed", true);
                        player.startConversation(new GypsyArisDemonSlayerD(player, 1).getStart());
                        player.unlock();
                        stop();
                    }
                    if(tick != 7 && tick != 13 && tick != 14 && tick != 22 && tick != 23)
                        tick++;
                }
            }, 0, 1);
        });

    }

	@Override
	public boolean login() {
        System.out.println("Login");
        forceClose();
		return false;
	}

	@Override
    public boolean logout() {
        System.out.println("logout");
        removeInstance();
        player.unlock();
	    return false;
    }

    @Override
    public void forceClose() {
        System.out.println("Force close");
        player.setNextWorldTile(locationBeforeCutscene);
        removeInstance();
        player.unlock();
        removeController();
    }

    private void removeInstance() {
        try { instance.destroy(); }
        catch(Exception e) { ; }
    }
}

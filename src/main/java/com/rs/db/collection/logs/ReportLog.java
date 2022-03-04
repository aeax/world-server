package com.rs.db.collection.logs;

import com.rs.game.player.Player;
import com.rs.utils.ReportsManager;

public class ReportLog {
	private String player;
	private String reporter;
	private ReportsManager.Rule rule;

	public ReportLog(Player reporter, Player reported, ReportsManager.Rule rule) {
		this.player = reported.getUsername();
		this.reporter = reporter.getUsername();
		this.rule = rule;
	}

	//TODO create hashCode and equals
}

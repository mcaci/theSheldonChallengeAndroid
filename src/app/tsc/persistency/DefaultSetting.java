package app.tsc.persistency;

enum DefaultSetting {
	PLAYER_1_NAME {
		@Override
		public final String getValue() {
			return "Player";
		}
	},
	PLAYER_2_NAME {
		@Override
		public String getValue() {
			return "AI";
		}
	},
	MAX_SCORE {
		@Override
		public final String getValue() {
			return "3";
		}
	},
	LANGUAGE {
		@Override
		public final String getValue() {
			return "ENGLISH";
		}
	},
	RULE {
		@Override
		public final String getValue() {
			return "CLASSIC";
		}
	};

	public abstract String getValue();
}

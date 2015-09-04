CREATE TABLE goodtwist.challenge (
	id uuid,
	creator text,
	participanta text,
	participantb text,
	charactera text,
	characterb text,
	PRIMARY KEY (creator, id)
);
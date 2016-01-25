CREATE TABLE goodtwist.challenge (
        id uuid PRIMARY KEY,
        creator text,
        participanta text,
        participantb text,
        charactera text,
        characterb text,
        cash float,
        endtime int,
        game int,
        joindatea int,
        joindateb int
        );

CREATE TABLE goodtwist.useraccount (
        username text PRIMARY KEY,
        onlinestatus boolean,
        email text,
        password text,
        friends list<text>,
        membershipstatus int,
        confirmationkey uuid
        );

CREATE TABLE goodtwist.transaction (
        method text,
        id uuid PRIMARY KEY,
        username text,
        direction boolean,
        externalid text,
        date int
        );

CREATE TABLE goodtwist.gamelink (
        username text,
        game int,
        region text,
        PRIMARY KEY ((username), game, region)
        );
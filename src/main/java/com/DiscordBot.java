package com;

import com.Discord.DiscordBot.A_IndividualMethods.BadWordChecker;
import com.Discord.DiscordBot.Constants;
import com.Discord.DiscordBot.Sessions.SessionCleanupService;
import com.Discord.DiscordBot.Units.ActiveQuestionTracker;
import com.Discord.DiscordBot.Units.QuestionBank;
import com.Discord.DiscordBot.commands.CommandManager;
import com.Discord.DiscordBot.listeners.ButtonListener;
import com.Discord.DiscordBot.listeners.JoinGuild;
import com.Discord.DiscordBot.listeners.UnitListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    private final ShardManager shardManager;
    private final ScheduledExecutorService scheduler;

    public DiscordBot() throws LoginException {
        Dotenv config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("youtube"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);

        shardManager = builder.build();

        // Register event listeners
        shardManager.addEventListener(new CommandManager());
        shardManager.addEventListener(new UnitListener());
        shardManager.addEventListener(new ButtonListener());
        shardManager.addEventListener(new JoinGuild());

        CommandManager.initializeCommands(); // Initialize commands here

        // Checks every 30 seconds for any questions that may be expired to remove
        scheduler.scheduleAtFixedRate(() -> {
                ActiveQuestionTracker.checkForExpiredQuestions(shardManager); // shardManager never null, so this always works
        }, 0, Constants.unitQuestionIntervalCheckInSeconds, TimeUnit.SECONDS); // Checks every 30 seconds

        // Initialize session cleanup
        // Check every 5 minutes
        // 30 minute timeout
        SessionCleanupService sessionCleanupService = new SessionCleanupService(
                shardManager,
                Constants.testIntervalCheckInMinutes,    // Check every 5 minutes
                Constants.testTimeoutInMinutes    // 30 minute timeout
        );
        sessionCleanupService.startCleanupTask();

    }

    public static void main(String[] args) {
        try {
            DiscordBot bot = new DiscordBot();
            QuestionBank questionBank = new QuestionBank(); // Needs to be here to initialize questions lol
            System.out.println("This is the AP " + Constants.slashPrefix.toUpperCase() + " bot which has " + Constants.numUnits + " units");

            // Better to be safe than sorry?
            Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
        } catch (LoginException e) {
            System.out.println("Error: Invalid bot token - check your .env file");
        } catch (Exception e) {
            System.out.println("Error: Bot failed to start");
            e.printStackTrace();
        }
    }

    // Little shutdown method
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        if (shardManager != null) {
            shardManager.shutdown();
        }
    }

}